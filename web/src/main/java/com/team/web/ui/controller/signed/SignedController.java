package com.team.web.ui.controller.signed;

import com.team.web.shared.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller @RequestMapping("signed") public class SignedController {

    /**
     * This method gets the {@link User} from the <tt>/signin</tt> page in
     * {@link com.team.web.ui.controller.sign.SignInController#submitForm(UserDTO,
     * HttpServletRequest, RedirectAttributes)}, and shows the <i>correct</i>
     * <tt>/signed</tt> page accordingly.
     *
     * @param request the HTTP GET request.
     * @return the <i>signed</i> <tt>HTML</tt> page.
     * @see com.team.web.ui.controller.sign.SignInController#submitForm(UserDTO,
     * HttpServletRequest, RedirectAttributes)
     */
    @GetMapping public String signed(HttpServletRequest request, Model model) {

        // Get the input flash-map:
        Map<String, ?> inputFlashMap =
                RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            User user = (User) inputFlashMap.get("userSigned");
            model.addAttribute("userSigned", user);

            // Show the 'signed' page:
            return "mainweb/signed";
        } else {
            return "redirect:/signin";
        }
    }

}
