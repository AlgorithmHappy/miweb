package dev.gerardomarquez.mail_whatsapp_send.services;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ApiResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.InformationPostResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.PageResponse;
import dev.gerardomarquez.mail_whatsapp_send.entities.PostEntity;
import dev.gerardomarquez.mail_whatsapp_send.entities.RelationPostEntity;
import dev.gerardomarquez.mail_whatsapp_send.entities.TagEntity;
import dev.gerardomarquez.mail_whatsapp_send.repositories.PostsCrud;
import dev.gerardomarquez.mail_whatsapp_send.repositories.TagsCrud;
import dev.gerardomarquez.mail_whatsapp_send.utils.Constants;

/**
 * Clase que implementa de ServicePostsCrud para poder gestionar la tabla de Posts en base
 * de datos
 */
@Service
public class ImplementationServicePostsCrud implements ServicePostsCrud {

    @Autowired
    private PostsCrud postsCrud;

    @Autowired
    private TagsCrud tagsCrud;

    /*
     * Asunto a donde se va enviar
     */
    @Value("${date.format}")
    private String dateFormat;

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiResponse<PageResponse<InformationPostResponse> > findAllByPageAndTitleAndTag(
        Pageable pageable,
        String tag,
        String title
    ) {
        pageable = mapSort(pageable);
        Page<PostEntity> pagePostEntity;

        if(tag == null && title == null){
            pagePostEntity = postsCrud.findAll(pageable);
        } else if(tag == null && title != null){
            pagePostEntity = postsCrud.findByTitleContainingIgnoreCase(title, pageable);
        } else if(title == null && tag != null){
            List<TagEntity> tags = tagsCrud.findByNameContainingIgnoreCase(tag);
            List<Integer> idTags = tags.stream().map(
                (TagEntity it) -> {
                    return it.getId();
                }
            ).collect(Collectors.toList() );

            pagePostEntity = postsCrud.findDistinctByTags_IdIn(idTags, pageable);
        } else {
            List<TagEntity> tags = tagsCrud.findByNameContainingIgnoreCase(tag);
            List<Integer> idTags = tags.stream().map(
                (TagEntity it) -> {
                    return it.getId();
                }
            ).collect(Collectors.toList() );

            pagePostEntity = postsCrud.findDistinctByTitleContainingIgnoreCaseAndTags_IdIn(title, idTags, pageable);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        Page<InformationPostResponse> responsePage = pagePostEntity.map(
            (PostEntity it) -> {
                Optional<RelationPostEntity> nextPostOptional = it
                    .getRelationPost()
                    .stream()
                    .filter(jt -> !jt.getIsInPostList() )
                    .findFirst();
                String nextPostTitle = new String();    
                if(nextPostOptional.isPresent() && nextPostOptional.get().getNextPost() != null)
                    nextPostTitle = nextPostOptional.get().getNextPost().getTitle();

                Optional<RelationPostEntity> previousPostOptional = it
                    .getRelationPost()
                    .stream()
                    .filter(jt -> !jt.getIsInPostList() )
                    .findFirst();
                String previousPostTitle = new String();    
                if(previousPostOptional.isPresent() && previousPostOptional.get().getPreviousPost() != null)
                    previousPostTitle = previousPostOptional.get().getPreviousPost().getTitle();



                return new InformationPostResponse(
                    it.getTitle(),
                    it.getCreatedAt().format(formatter),
                    it.getAverageReadDuration(),
                    it.getDescription(),
                    it.getTags().stream().map(jt -> jt.getName() ).collect(Collectors.toSet() ),
                    it.getLinkImage(),
                    it.getAltImage(),
                    it.getLinkRawMarkdown(),
                    nextPostTitle,
                    previousPostTitle
                );
            }
        );

        return new ApiResponse<>(
            true,
            Constants.SUCCESS,
            new PageResponse<>(
                responsePage.getContent(),
                responsePage.getNumber(),
                responsePage.getSize(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages()
            )
        );
    }

    /**
     * Metodo que mapea los valores de ordenamiento dependiendo los dtos, para no ordenar por atributos de
     * entity si no mas bien por dto
     * @param pageable Paginacion que se recive desde el request
     * @return Paginacion ya mapeado correctamente para la entidad
     */
    private Pageable mapSort(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            return pageable;
        }

        List<Sort.Order> orders = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            String property = order.getProperty();

            
        if (!Constants.SORT_MAPPING.containsKey(property) ) {
            throw new IllegalArgumentException("Parametro incorrecto: " + property);
        }


            if (property.equals("date")) {
                property = "createdAt";
            }
            if (property.equals("title")) {
                property = "title";
            }

            orders.add(new Sort.Order(order.getDirection(), property));
        }

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertOne(String owner, String repo, String branch, String filePath) {
        PostEntity newPost = new PostEntity();
        newPost.setTitle(filePath.split("/")[0]);
        newPost.setAverageReadDuration(Constants.GENERIC_AVERAGE_DURATION);
        newPost.setDescription(Constants.GENERIC_POST_DESCRIPTION);
        String linkRawMarkDown = Constants.URL_RAW_MARK_DOWN.formatted(owner, repo, branch, Constants.encodePath(filePath) );
        newPost.setLinkRawMarkdown(linkRawMarkDown);
        postsCrud.save(newPost);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOne(String filePath) {
        String title = filePath.split("/")[0];

        Optional<PostEntity> post = postsCrud.findFirstByTitle(title);
        if(post.isPresent() ){
            postsCrud.delete(post.get() );
        }
    }

}
