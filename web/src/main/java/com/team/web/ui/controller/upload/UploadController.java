package com.team.web.ui.controller.upload;

import com.team.web.shared.dto.UserDTO;
import engine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import user.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * GUIDE: https://www.youtube.com/watch?v=U5JXDnMJVyo
 */
@Controller public class UploadController {

    @Autowired ServletContext servletContext;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String show() {
        return "upload/upload";
    }

    /**
     * Get the file from the POST request and save it to the
     * <tt>/resources/upload/</tt> path within the server.
     *
     * @param file     the file submitted in the POST request.
     * @param userSigned     the signed-in {@link User} who uploaded the file.
     * @param modelMap to add an attribute to the next <tt>html</tt> page.
     * @return the next <tt>html</tt> page.
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RedirectView submit(@RequestParam("file") MultipartFile file,
                         @RequestParam("signedUser") User userSigned,
                         HttpServletRequest request,
                         RedirectAttributes redirectAttributes) {
        if (file != null) {

            // Get the destination path.
            String fileName =
                    servletContext.getRealPath("") + "/resources/upload/" +
                            file.getOriginalFilename();

            // Create a new file in the destination path.
            File ioFile = new File(fileName);

            // Create the directories needed in order to navigate to the path.
            ioFile.mkdirs();
            try {

                /*
                 * Transfer the file submitted in the POST request to the
                 * desired destination path.
                 */
                file.transferTo(ioFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        redirectAttributes.addFlashAttribute("userSigned", userSigned);

        return new RedirectView("signed", true);
    }


    /**
     * After a success POST, redirects the view to the <tt>/signed</tt>
     * context-path, with {@code attributes}.
     * <p>
     * GUIDE HERE: https://www.baeldung.com/spring-web-flash-attributes
     *
     * @param requestUserDTO     the POSTed {@link UserDTO}.
     * @param request            the HTTP request.
     * @param redirectAttributes enables to redirect the given {@link UserDTO}
     *                           to another path.
     * @return a {@link RedirectView} to <tt>/signed</tt> path, with {@link
     * org.springframework.web.servlet.FlashMap} {@code attributes}.
     */
    @PostMapping public RedirectView submitForm(
            @ModelAttribute("requestUserDTO") UserDTO requestUserDTO,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {

        /*
         * Get the requestUser's Holdings, and insert them as an attribute
         * to the "signed" HTML file.
         */
        User userSigned = Engine.findUserByNameForced(requestUserDTO.getName());

        redirectAttributes.addFlashAttribute("userSigned", userSigned);

        return new RedirectView("signed", true);
    }

}
