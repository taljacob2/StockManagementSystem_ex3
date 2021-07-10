package com.team.web.ui.controller.sign;

import com.team.web.service.UserService;
import com.team.web.shared.dto.UserDTO;
import engine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import user.User;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * After a success POST, redirects the view to the <tt>/signed</tt>
     * context-path, with {@code attributes}.
     * <p>
     * GUIDE HERE: https://www.baeldung.com/spring-web-flash-attributes
     *
     * @param requestUserDTO     the POSTed {@link UserDTO}.
     * @param request            the HTTP request.
     * @param redirectAttributes enables to redirect the given {@link UserDTO}
     *                           to another path.
     * @return a {@link RedirectView} to <tt>/signed</tt> path, with {@link
     * org.springframework.web.servlet.FlashMap} {@code attributes}.
     */
    @PostMapping public RedirectView submitForm(
            @ModelAttribute("requestUserDTO") UserDTO requestUserDTO,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {

        /*
         * Get the requestUser's Holdings, and insert them as an attribute
         * to the "signed" HTML file.
         */
        User userSigned = Engine.findUserByNameForced(requestUserDTO.getName());

        redirectAttributes.addFlashAttribute("userSigned", userSigned);

        return new RedirectView("signed", true);
    }

}
