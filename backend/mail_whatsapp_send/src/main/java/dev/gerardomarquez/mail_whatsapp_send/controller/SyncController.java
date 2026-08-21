package dev.gerardomarquez.mail_whatsapp_send.controller;
import dev.gerardomarquez.mail_whatsapp_send.services.ImplementationServiceRebuildAndDeploy;
import dev.gerardomarquez.mail_whatsapp_send.services.ServicePresentationIndex;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.gerardomarquez.mail_whatsapp_send.dtos.HedgeDocNotesNoPublic;
import dev.gerardomarquez.mail_whatsapp_send.dtos.RepositorySection;
import dev.gerardomarquez.mail_whatsapp_send.errors.GitHubException;
import dev.gerardomarquez.mail_whatsapp_send.errors.HedgeDocException;
import dev.gerardomarquez.mail_whatsapp_send.errors.SyncException;
import dev.gerardomarquez.mail_whatsapp_send.services.ServiceSync;
import dev.gerardomarquez.mail_whatsapp_send.utils.Constants;

/**
 * Controller del panel de sincronización entre HedgeDoc y GitHub.
 * Protegido por Spring Security, accesible únicamente en /sync.
 */
@Controller
@RequestMapping("/sync")
public class SyncController {
    private final ServiceSync serviceSync;
    private final MessageSource messageSource;
    private final ServicePresentationIndex servicePresentationIndex;
    private final ImplementationServiceRebuildAndDeploy serviceRebuildAndDeploy;

    @Value("${hedgedoc.url}")
    private String hedgedocUrl;

    /**
     * @param serviceSync   Servicio orquestador de sincronización
     * @param messageSource Para obtener mensajes desde messages.properties
     */
    public SyncController(
        ServiceSync serviceSync,
        MessageSource messageSource,
        ServicePresentationIndex servicePresentationIndex,
        ImplementationServiceRebuildAndDeploy serviceRebuildAndDeploy
    ) {
        this.serviceSync = serviceSync;
        this.messageSource = messageSource;
        this.servicePresentationIndex = servicePresentationIndex;
        this.serviceRebuildAndDeploy = serviceRebuildAndDeploy;
    }

    /**
     * Muestra el panel principal de sincronización.
     * Lista todos los pares nota-repo agrupados por repositorio.
     *
     * @param model Modelo de Thymeleaf
     * @return Vista sync/index
     */
    @GetMapping
    public String index(Model model) {

        List<RepositorySection> repositorySections = servicePresentationIndex.getRowsNotesForPublicPosts();
        List<HedgeDocNotesNoPublic> notesNoPublic = servicePresentationIndex.getAllNotesNoPublic();

        model.addAttribute("repositorySections", repositorySections);
        model.addAttribute("notesNoPublic", notesNoPublic);
        model.addAttribute("hedgedocUrl", hedgedocUrl);

        return "sync/index";
    }

    /**
     * Ejecuta un Pull para un post específico.
     * Obtiene el contenido del archivo en GitHub y crea
     * una nota nueva en HedgeDoc con ese contenido.
     *
     * @param idPost             ID del post a sincronizar
     * @param redirectAttributes Atributos para pasar mensajes a la vista tras el redirect
     * @return Redirect al panel /sync
     */
    @PostMapping("/pull/{idPost}")
    public String pull(
        @PathVariable("idPost") Integer idPost,
        RedirectAttributes redirectAttributes
    ) {
        try {
            String noteId = serviceSync.pull(idPost);
            redirectAttributes.addFlashAttribute(
                Constants.SUCCESS,
                messageSource.getMessage(
                    Constants.ERR_MSG_CONTROLLER_SYNC_PULL_SUCCESS,
                    new Object[]{noteId},
                    LocaleContextHolder.getLocale()
                )
            );
        } catch (GitHubException e) {
            redirectAttributes.addFlashAttribute(
                Constants.ERROR,
                messageSource.getMessage(
                    Constants.ERR_MSG_CONTROLLER_SYNC_PULL_GITHUB,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                )
            );
        } catch (HedgeDocException e) {
            redirectAttributes.addFlashAttribute(
                Constants.ERROR,
                messageSource.getMessage(
                    Constants.ERR_MSG_CONTROLLER_SYNC_PULL_HEDGEDOC,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                )
            );
        } catch (SyncException e) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage() );
        }

        return "redirect:/sync";
    }

    /**
     * Ejecuta un Push para un post específico.
     * Obtiene el contenido de la nota en HedgeDoc y lo
     * sube al archivo correspondiente en GitHub.
     *
     * @param idPost             ID del post a sincronizar
     * @param commitMessage      Mensaje del commit en GitHub
     * @param redirectAttributes Atributos para pasar mensajes a la vista tras el redirect
     * @return Redirect al panel /sync
     */
    @PostMapping("/push/{idPost}")
    public String push(
            @PathVariable("idPost") Integer idPost,
            @RequestParam(value = "commitMessage", required = true) String commitMessage,
            RedirectAttributes redirectAttributes
    ) {
        try {
            serviceSync.push(idPost, commitMessage);
            redirectAttributes.addFlashAttribute(
                Constants.SUCCESS,
                messageSource.getMessage(
                    Constants.ERR_MSG_CONTROLLER_SYNC_PUSH_SUCCESS,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                )
            );
        } catch (GitHubException e) {
            redirectAttributes.addFlashAttribute(
                Constants.ERROR,
                messageSource.getMessage(
                    Constants.ERR_MSG_CONTROLLER_SYNC_PUSH_GITHUB,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                )
            );
        } catch (HedgeDocException e) {
            redirectAttributes.addFlashAttribute(
                Constants.ERROR,
                messageSource.getMessage(
                    Constants.ERR_MSG_CONTROLLER_SYNC_PUSH_HEDGEDOC,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                )
            );
        } catch (SyncException e) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage() );
        }

        return "redirect:/sync";
    }

    /**
     * Muestra el formulario de login del panel de sync.
     *
     * @param error   Presente si el login falló
     * @param logout  Presente si el usuario cerró sesión
     * @param model   Modelo de Thymeleaf
     * @return Vista sync/login
     */
    @GetMapping("/login")
    public String login(
        @RequestParam(value = "error", required = false) String error,
        @RequestParam(value = "logout", required = false) String logout,
        Model model
    ) {
        if (error != null) {
            model.addAttribute(
                Constants.ERROR,
                messageSource.getMessage(
                    Constants.ERR_MSG_CONTROLLER_SYNC_LOGIN_ERROR,
                    null,
                    LocaleContextHolder.getLocale()
                )
            );
        }
        if (logout != null) {
            model.addAttribute(
                Constants.SUCCESS,
                messageSource.getMessage(
                    Constants.ERR_MSG_CONTROLLER_SYNC_LOGOUT_ERROR,
                    null,
                    LocaleContextHolder.getLocale()
                )
            );
        }
        return "sync/login";
    }

    /**
     * Ejecuta un Pull para un post específico.
     * Obtiene el contenido del archivo en GitHub y crea
     * una nota nueva en HedgeDoc con ese contenido.
     *
     * @param idPost             ID del post a sincronizar
     * @param redirectAttributes Atributos para pasar mensajes a la vista tras el redirect
     * @return Redirect al panel /sync
     */
    @PostMapping("/download/{idRepository}")
    public String syncRepositories(
        @PathVariable("idRepository") Integer idRepository,
        RedirectAttributes redirectAttributes
    ) {
        serviceSync.updateAllFilesInRepository(idRepository);
        return "redirect:/sync";
    }

    @PostMapping("/rebuild")
    public String vercelRebuildAndDeploy(RedirectAttributes redirectAttributes){
        try{
            serviceRebuildAndDeploy.rebuildAndDeploy();
            redirectAttributes.addFlashAttribute(
                Constants.SUCCESS,
                messageSource.getMessage(
                    Constants.ERR_MSG_CONTROLLER_SYNC_PUSH_SUCCESS,
                    new Object[]{},
                    LocaleContextHolder.getLocale()
                )
            );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage() );
        }
        return "redirect:/sync";
    }

    @PostMapping("/publicar/{shortId}")
    public String postMethodName(
        @PathVariable("shortId") String shortId,
        @RequestParam(value = "repositoryId", required = true) Integer repositoryId,
        @RequestParam(value = "commitMessage", required = true) String commitMessage,
        RedirectAttributes redirectAttributes
    ) {
        try {
            serviceSync.push(shortId, repositoryId, commitMessage);
            redirectAttributes.addFlashAttribute(
                Constants.SUCCESS,
                messageSource.getMessage(
                    Constants.ERR_MSG_CONTROLLER_SYNC_PUSH_SUCCESS,
                    new Object[]{shortId},
                    LocaleContextHolder.getLocale()
                )
            );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage());
        }
        return "redirect:/sync";
    }
    
    @PostMapping("/publicar/socialMedia/{idPost}")
    public String socialMediaShare(
        @PathVariable("idPost") Integer idPost,
        @RequestParam(value = "repositoryId", required = true) Integer repositoryId,
        RedirectAttributes redirectAttributes
    ) {
        /*try {
            serviceSync.socialMediaShare(shortId, repositoryId);
            redirectAttributes.addFlashAttribute(
                Constants.SUCCESS,
                messageSource.getMessage(
                    Constants.ERR_MSG_CONTROLLER_SYNC_PUSH_SUCCESS,
                    new Object[]{shortId},
                    LocaleContextHolder.getLocale()
                )
            );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, e.getMessage());
        }*/
        return "redirect:/sync";
    }

}
