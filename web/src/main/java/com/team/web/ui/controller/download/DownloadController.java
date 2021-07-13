package com.team.web.ui.controller.download;

import com.team.web.service.JaxbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;


/**
 * <ul>
 * <li>DOWNLOAD AND SAVE FILE GUIDE: https://www.youtube.com/watch?v=QUT9vd-YhQ0&ab_channel=ChargeAhead
 * </li>
 * </ul>
 */
@Controller @RequestMapping("download") class DownloadController {

    @Autowired ServletContext servletContext;

    @Autowired JaxbService jaxbService;

    @GetMapping public String submit(Model model) {
        jaxbService.marshal(
                "C:/Users/Tal/C_Code/java/rolling_ex_3/downloaded.xml");

        // File folder = new File()


        return "download/download";
    }

    // /**
    //  * Get the file from the POST request and save it to the
    //  * <tt>/resources/upload/</tt> path within the server.
    //  *
    //  * @param file     the file submitted in the POST request.
    //  * @param userDTO  this is a {@link UserDTO} that the <tt>/upload</tt>
    //  *                 <i>form</i> is sending through a POST method.
    //  * @param modelMap to add an attribute to the next <tt>html</tt> page.
    //  * @return the next <tt>html</tt> page.
    //  */
    // @RequestMapping(method = RequestMethod.POST) public String submit(
    //         @RequestParam("file") MultipartFile file,
    //         @ModelAttribute("userDTO") UserDTO userDTO, ModelMap modelMap)
    //         throws IOException {
    //     if (file != null) {
    //
    //         // Get the destination path.
    //         String fileName = servletContext.getRealPath("/resources/upload/") +
    //                 file.getOriginalFilename();
    //
    //         // Create a new file in the destination path.
    //         File ioFile = new File(fileName);
    //
    //         /*
    //          * Transfer the file submitted in the POST request to the
    //          * desired destination path.
    //          */
    //         FileUtils.copyInputStreamToFile(file.getInputStream(), ioFile);
    //
    //         // Unmarshal file:
    //         User userToUnmarshalTo =
    //                 Engine.findUserByNameForced(userDTO.getName());
    //         jaxbService.unmarshal(userToUnmarshalTo, fileName);
    //     }
    //
    //
    //     // Add an attribute for the new HTML file, and refer to it.
    //     modelMap.addAttribute("file", file);
    //     return "upload/fileUploadView";
    // }

}
