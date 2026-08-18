package dev.gerardomarquez.mail_whatsapp_send.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.gerardomarquez.mail_whatsapp_send.dtos.NoteEnabled;
import dev.gerardomarquez.mail_whatsapp_send.dtos.RepositorySection;
import dev.gerardomarquez.mail_whatsapp_send.dtos.RowNotesForPublicPosts;
import dev.gerardomarquez.mail_whatsapp_send.entities.PostSyncEntity;
import dev.gerardomarquez.mail_whatsapp_send.entities.RepositoryEntity;
import dev.gerardomarquez.mail_whatsapp_send.repositories.HedgeDocDataBaseCrud;
import dev.gerardomarquez.mail_whatsapp_send.repositories.PostsSyncCrud;

/**
 * {@inheritdoc}
 */
@Service
public class ImplementationServicePresentationIndex implements ServicePresentationIndex {

    private final PostsSyncCrud postSyncCrud;
    private final HedgeDocDataBaseCrud hedgedocDataBaseCrud;

    public ImplementationServicePresentationIndex(
        PostsSyncCrud postSyncCrud,
        HedgeDocDataBaseCrud hedgedocDataBaseCrud
    ) {
        this.postSyncCrud = postSyncCrud;
        this.hedgedocDataBaseCrud = hedgedocDataBaseCrud;
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public List<RepositorySection> getRowsNotesForPublicPosts() {
        // Obtener todos los pares y agruparlos por repositorio
        Map<RepositoryEntity, List<PostSyncEntity> > postsSyncByRepo = postSyncCrud.findAll()
            .stream()
            .collect(Collectors.groupingBy(PostSyncEntity::getRepository) );

        List<RepositorySection> repositorySections = new ArrayList<>();

        for (Map.Entry<RepositoryEntity, List<PostSyncEntity> > entry : postsSyncByRepo.entrySet() ) {
            List<String> shortids = entry.getValue().stream()
                .map(PostSyncEntity::getHedgedocNoteId)
                .collect(Collectors.toList() );

            List<NoteEnabled> listNoteEnableds = hedgedocDataBaseCrud.getNotePermission(shortids);
            List<RowNotesForPublicPosts> rowsNotesForPublicPosts = new ArrayList<>();

            for(NoteEnabled noteEnabled : listNoteEnableds) {
                PostSyncEntity postSyncEntity = entry.getValue().stream()
                    .filter(it -> it.getHedgedocNoteId().equals(noteEnabled.noteId() ) )
                    .findFirst()
                    .orElse(new PostSyncEntity() );

                rowsNotesForPublicPosts.add(
                    new RowNotesForPublicPosts(
                        postSyncEntity.getIdPost(),
                        noteEnabled.noteId(),
                        postSyncEntity.getPost().getTitle(),
                        postSyncEntity.getFilePath(),
                        postSyncEntity.getLastSyncedAt(),
                        noteEnabled.enabled()
                    )
                );
            }

            RepositorySection section = new RepositorySection(
                entry.getKey().getId(),
                entry.getKey().getOwner(),
                entry.getKey().getName(),
                entry.getKey().getDefaultBranch(),
                rowsNotesForPublicPosts
            );

            repositorySections.add(section);
        }

        return repositorySections;
    }

}
