package com.team.web.ui.controller.index;

import com.team.web.shared.dto.UserDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController @RequestMapping(value = {"/", "index", "home"})
public class IndexController {

    @GetMapping public ModelAndView index(Model model) {

        model.addAttribute("userDTO", new UserDTO());

        // Show the 'index' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mainweb/home");
        return modelAndView;
    }

}
