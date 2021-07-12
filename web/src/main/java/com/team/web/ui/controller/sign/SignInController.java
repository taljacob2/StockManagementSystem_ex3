package com.team.web.ui.controller.sign;

import com.team.web.service.UserService;
import com.team.web.shared.dto.UserDTO;
import engine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import user.User;

import java.util.List;
import java.util.stream.Collectors;

@RestController @RequestMapping("signin") public class SignInController {

    @Autowired UserService userService;

    @GetMapping public ModelAndView showForm(Model model) {

        /*
         * Create a new 'requestUserDTO', and send it to the 'signin' form
         * to be filled via thymeleaf.
         */
        UserDTO requestUserDTO = new UserDTO();
        model.addAttribute("requestUserDTO", requestUserDTO);

        /*
         * Get all a list of all the userNames from the Engine and send it to
         * thymeleaf.
         */
        boolean usersNameListIsPresent = Engine.isUsers();
        try {
            List<String> usersNameList = Engine.getUsers().getCollection().
                    stream().map(User::getName).collect(Collectors.toList());
            model.addAttribute("usersNameList", usersNameList);
        } catch (java.io.IOException ignored) {}

        model.addAttribute("usersNameListIsPresent", usersNameListIsPresent);

        // Show the 'signin' form:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sign/signin");
        return modelAndView;
    }

    @PostMapping public ModelAndView submitForm() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/signed");
        return modelAndView;
    }

}
