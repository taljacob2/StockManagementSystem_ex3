package com.team.web.ui.controller.sign;

import com.team.web.service.UserService;
import shared.dto.UserDTO;
import engine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import user.User;

import java.util.List;
import java.util.stream.Collectors;

@RestController @RequestMapping("signup") public class SignUpController {

    @Autowired UserService userService;

    @GetMapping public ModelAndView showForm(Model model) {

        /*
         * Create a new 'requestUserDTO', and send it to the 'signup' form
         * to be filled via thymeleaf.
         */
        UserDTO requestUserDTO = new UserDTO();
        model.addAttribute("requestUserDTO", requestUserDTO);

        /*
         * Get all a list of all the userNames from the Engine and send it to
         * thymeleaf.
         */
        boolean usersNameListIsPresent = Engine.isUsersNotEmpty();
        model.addAttribute("usersNameListIsPresent", usersNameListIsPresent);

        if (usersNameListIsPresent) {
            List<String> usersNameList =
                    Engine.getUsersForced().getCollection().
                            stream().map(User::getName)
                            .collect(Collectors.toList());
            model.addAttribute("usersNameList", usersNameList);
        }

        // Show the 'signup' form:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sign/signup");
        return modelAndView;
    }

    @PostMapping public ModelAndView submitForm(
            @ModelAttribute("requestUserDTO") UserDTO requestUserDTO) {

        /*
         * Create a User Entity from the data in the DTO, and store its data
         * in the Engine's Users database
         */
        userService.createUser(requestUserDTO);

        // Show the 'signup' submit page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sign/submit/signup_submit");
        return modelAndView;
    }

}
