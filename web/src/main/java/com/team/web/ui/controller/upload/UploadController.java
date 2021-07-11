package com.team.web.ui.controller.upload;

import com.team.web.service.JaxbService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import user.User;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;


/**
 * <ul>
 * <li>UPLOAD AND SAVE FILE GUIDE: https://www.youtube.com/watch?v=U5JXDnMJVyo
 * </li>
 * <li>AJAX GUIDE: https://www.youtube.com/watch?v=Y_w9KjOrEXk&ab_channel=JavaTechie
 * </li>
 * </ul>
 */
@Controller public class UploadController {

    @Autowired ServletContext servletContext;

    @Autowired JaxbService jaxbService;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String submit(@RequestParam("userSigned") User userSigned,
                         Model model) {
        model.addAttribute("userSigned", userSigned);
        return "upload/upload";
    }

    /**
     * Get the file from the POST request and save it to the
     * <tt>/resources/upload/</tt> path within the server.
     *
     * @param file     the file submitted in the POST request.
     * @param modelMap to add an attribute to the next <tt>html</tt> page.
     * @return the next <tt>html</tt> page.
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile file,
                         @RequestParam("userSigned") User userSigned,
                         ModelMap modelMap) throws IOException {
        if (file != null) {

            // Get the destination path.
            String fileName = servletContext.getRealPath("/resources/upload/") +
                    file.getOriginalFilename();

            // Create a new file in the destination path.
            File ioFile = new File(fileName); // TODO: remove

            /*
             * Transfer the file submitted in the POST request to the
             * desired destination path.
             */
            FileUtils.copyInputStreamToFile(file.getInputStream(), ioFile);


            System.out.println(fileName); // DE-BUG
            System.out.println(ioFile);


            // Unmarshall file:
            // MenuUI.command_LOAD_XML_FILE(fileName); // DE-BUG

            jaxbService.unmarshal(userSigned, fileName);

        }


        // Add an attribute for the new HTML file, and refer to it.
        modelMap.addAttribute("file", file);
        return "upload/fileUploadView";
    }

}
