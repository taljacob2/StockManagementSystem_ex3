package com.team.web.ui.controller.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

/* XXX GUIDE:
    https://www.youtube.com/watch?v=U5JXDnMJVyo */

@Controller public class UploadController {

    @Autowired ServletContext servletContext;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String submit() {
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
                         ModelMap modelMap) {
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

        // Add an attribute for the new HTML file, and refer to it.
        modelMap.addAttribute("file", file);
        return "upload/fileUploadView";
    }

}
