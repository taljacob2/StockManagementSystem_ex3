package com.team.web.ui.controller.upload;

import com.team.shared.dto.UserDTO;
import com.team.shared.engine.data.user.User;
import com.team.shared.engine.engine.Engine;
import com.team.web.service.JaxbService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;


/**
 * <ul>
 * <li>UPLOAD AND SAVE FILE GUIDE: https://www.youtube.com/watch?v=U5JXDnMJVyo
 * </li>
 * <li>AJAX GUIDE: https://www.youtube.com/watch?v=Y_w9KjOrEXk&ab_channel=JavaTechie
 * </li>
 * </li>
 * <li>Lombok DOC GUIDE: https://projectlombok.org/features/all
 * </li>
 * </ul>
 */
@Slf4j @Controller @RequestMapping("upload") class UploadController {

    @Autowired ServletContext servletContext;

    @Autowired JaxbService jaxbService;

    @RequestMapping(method = RequestMethod.GET)
    public String submit(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "upload/upload";
    }

    /**
     * Get the file from the POST request and save it to the
     * <tt>/resources/upload/</tt> path within the server.
     *
     * @param file     the file submitted in the POST request.
     * @param userDTO  this is a {@link UserDTO} that the <tt>/upload</tt>
     *                 <i>form</i> is sending through a POST method.
     * @param modelMap to add an attribute to the next <tt>html</tt> page.
     * @return the next <tt>html</tt> page.
     */
    @RequestMapping(method = RequestMethod.POST) public String submit(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("userDTO") UserDTO userDTO, ModelMap modelMap)
            throws IOException {
        if (file != null) {

            // Get the destination path.
            String fileName = servletContext.getRealPath("/resources/upload/") +
                    file.getOriginalFilename();

            // Create a new file in the destination path.
            File ioFile = new File(fileName);

            /*
             * Transfer the file submitted in the POST request to the
             * desired destination path.
             */
            FileUtils.copyInputStreamToFile(file.getInputStream(), ioFile);

            // Unmarshal file:
            User userToUnmarshalTo =
                    Engine.findUserByNameForced(userDTO.getName());
            jaxbService.unmarshal(userToUnmarshalTo, fileName);

            log.warn("uploadingUser.notif.coll {}",
                    userToUnmarshalTo.getNotifications().getCollection());
            // debug
        }


        // Add an attribute for the new HTML file, and refer to it.
        modelMap.addAttribute("file", file);
        return "upload/fileUploadView";
    }

}
