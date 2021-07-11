package com.team.web.ui.controller.upload;

import com.team.web.service.JaxbService;
import com.team.web.shared.dto.UserDTO;
import com.team.web.ui.model.response.ServiceResponse;
import engine.Engine;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
@Controller @RequestMapping("upload") class UploadController {

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
     * @param modelMap to add an attribute to the next <tt>html</tt> page.
     * @return the next <tt>html</tt> page.
     */
    @RequestMapping(method = RequestMethod.POST) public String submit(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("userDTO") UserDTO userDTO, ModelMap modelMap)
            throws IOException {
        if (file != null) {

            // DE-BUG - checking printing output parameter from JS:
            System.out.println(userDTO.getName());

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

            System.out.println(fileName); // DE-BUG checking path is ok


            // Unmarshal file:
            User userToUnmarshalTo =
                    Engine.findUserByNameForced(userDTO.getName());
            jaxbService.unmarshal(userToUnmarshalTo, fileName);

            System.out.println("stocks=" + Engine.getStocks()); // DE-BUG check
            System.out.println("users=" + Engine.getUsers());   // DE-BUG check
        }


        // Add an attribute for the new HTML file, and refer to it.
        modelMap.addAttribute("file", file);
        return "upload/fileUploadView";
    }

    @GetMapping("ajax")
    public ResponseEntity<Object> getUser(@RequestBody String username) {
        ServiceResponse<User> response = new ServiceResponse<>("success",
                Engine.findUserByNameForced(username));
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

}
