package dev.gerardomarquez.mail_whatsapp_send.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.gerardomarquez.mail_whatsapp_send.dtos.HedgeDocNotesNoPublic;
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

                Boolean sharedPost = postSyncEntity.getPost().getShared();

                rowsNotesForPublicPosts.add(
                    new RowNotesForPublicPosts(
                        postSyncEntity.getIdPost(),
                        noteEnabled.noteId(),
                        postSyncEntity.getPost().getTitle(),
                        postSyncEntity.getFilePath(),
                        postSyncEntity.getLastSyncedAt(),
                        noteEnabled.enabled(),
                        sharedPost,
                        noteEnabled.permission()
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HedgeDocNotesNoPublic> getAllNotesNoPublic() {
        List<HedgeDocNotesNoPublic> allHedgeDocNotes = hedgedocDataBaseCrud.getAllNotes();

        List<PostSyncEntity> allPostSyncs = postSyncCrud.findAll();

        // Filtrar las notas que no están asociadas a ningún postSync
        List<HedgeDocNotesNoPublic> notesNoPublic = new ArrayList<>();
        for(HedgeDocNotesNoPublic note : allHedgeDocNotes) {
            boolean isPublic = false;
            for(PostSyncEntity postSync : allPostSyncs) {
                if(postSync.getHedgedocNoteId().equals(note.shortId() ) ) {
                    isPublic = true;
                    break;
                }
            }
            if(!isPublic) {
                notesNoPublic.add(note);
            }
        }

        return notesNoPublic;
    }
}
