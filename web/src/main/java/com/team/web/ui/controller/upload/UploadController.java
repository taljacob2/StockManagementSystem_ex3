package com.team.web.ui.controller.upload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/* XXX GUIDE:
    https://mkyong.com/spring-boot/spring-boot-file-upload-example/ */

@Controller public class UploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String submit(){
        return "upload/upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        modelMap.addAttribute("file", file);
        return "upload/fileUploadView";
    }

}
