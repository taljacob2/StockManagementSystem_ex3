package com.team.web.ui.controller.signed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController @RequestMapping("signed")  public class SignedController {

    @GetMapping public ModelAndView signed() {

        // Show the 'signed' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mainweb/signed");
        return modelAndView;
    }

}
