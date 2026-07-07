package dev.gerardomarquez.mail_whatsapp_send.services;

import java.time.LocalDateTime;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.gerardomarquez.mail_whatsapp_send.entities.PostSyncEntity;
import dev.gerardomarquez.mail_whatsapp_send.entities.RepositoryEntity;
import dev.gerardomarquez.mail_whatsapp_send.errors.GitHubException;
import dev.gerardomarquez.mail_whatsapp_send.errors.HedgeDocException;
import dev.gerardomarquez.mail_whatsapp_send.errors.SyncException;
import dev.gerardomarquez.mail_whatsapp_send.repositories.PostsSyncCrud;
import dev.gerardomarquez.mail_whatsapp_send.utils.Constants;

/**
 * Implementación del servicio orquestador de sincronización.
 * Coordina las llamadas a ServiceGitHub y ServiceHedgeDoc
 * para ejecutar el pull y el push de contenido Markdown.
 */
@Service
public class ImplementationServiceSync implements ServiceSync {
    private final ServiceGitHub serviceGitHub;
    private final ServiceHedgeDoc serviceHedgeDoc;
    private final PostsSyncCrud postSyncCrud;
    private final MessageSource messageSource;

    /**
     * @param serviceGitHub   Servicio para interactuar con la API de GitHub
     * @param serviceHedgeDoc Servicio para interactuar con la API de HedgeDoc
     * @param postSyncCrud    Repositorio JPA de posts_sync
     * @param messageSource   Para obtener mensajes de error desde messages.properties
     */
    public ImplementationServiceSync(
        ServiceGitHub serviceGitHub,
        ServiceHedgeDoc serviceHedgeDoc,
        PostsSyncCrud postSyncCrud,
        MessageSource messageSource
    ) {
        this.serviceGitHub = serviceGitHub;
        this.serviceHedgeDoc = serviceHedgeDoc;
        this.postSyncCrud = postSyncCrud;
        this.messageSource = messageSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public String pull(Integer idPost) {

        PostSyncEntity postSync = postSyncCrud.findById(idPost)
                .orElseThrow(
                    () -> new SyncException(
                        messageSource.getMessage(
                            Constants.ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND,
                            new Object[]{idPost},
                            LocaleContextHolder.getLocale()
                        )
                    )
                );

        RepositoryEntity repository = postSync.getRepository();

        try {
            String content = serviceGitHub.getFileContent(
                repository.getOwner(),
                repository.getName(),
                postSync.getFilePath(),
                repository.getDefaultBranch(),
                repository.getGithubToken()
            );

            String sha = serviceGitHub.getFileSha(
                repository.getOwner(),
                repository.getName(),
                postSync.getFilePath(),
                repository.getDefaultBranch(),
                repository.getGithubToken()
            );

            String newNoteId = serviceHedgeDoc.createNote(content);

            postSync.setHedgedocNoteId(newNoteId);
            postSync.setGithubSha(sha);
            postSync.setLastSyncedAt(LocalDateTime.now() );
            postSyncCrud.save(postSync);

            return newNoteId;

        } catch (GitHubException | HedgeDocException e) {
            throw e;
        } catch (Exception e) {
            throw new SyncException(
                messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                ),
                e
            );
        }
    }

    /**
     * Push: obtiene el contenido de la nota en HedgeDoc
     * y lo sube al archivo correspondiente en GitHub.
     * Actualiza github_sha y last_synced_at en posts_sync.
     *
     * @param idPost        ID del post a sincronizar
     * @param commitMessage Mensaje del commit en GitHub
     */
    @Override
    @Transactional
    public void push(Integer idPost, String commitMessage) {

        PostSyncEntity postSync = postSyncCrud.findById(idPost)
                .orElseThrow(
                    () -> new SyncException(
                        messageSource.getMessage(
                            Constants.ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND,
                            new Object[]{idPost},
                            LocaleContextHolder.getLocale()
                        )
                    )
                );

        if (postSync.getGithubSha() == null || postSync.getGithubSha().isBlank() ) {
            throw new SyncException(
                messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_SYNC_SHA_MISSING,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                )
            );
        }

        RepositoryEntity repository = postSync.getRepository();

        try {
            String content = serviceHedgeDoc.getNoteContent(postSync.getHedgedocNoteId() );

            String newSha = serviceGitHub.updateFileContent(
                repository.getOwner(),
                repository.getName(),
                postSync.getFilePath(),
                repository.getDefaultBranch(),
                repository.getGithubToken(),
                content,
                postSync.getGithubSha(),
                commitMessage
            );

            postSync.setGithubSha(newSha);
            postSync.setLastSyncedAt(LocalDateTime.now());
            postSyncCrud.save(postSync);

        } catch (GitHubException | HedgeDocException e) {
            throw e;
        } catch (Exception e) {
            throw new SyncException(
                messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                ),
                e
            );
        }
    }
}
