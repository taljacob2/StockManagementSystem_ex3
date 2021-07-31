package com.team.web.ui.controller.ajax.fragments.user.wallet;

import com.team.shared.engine.data.user.User;
import com.team.shared.engine.engine.Engine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Responding a {@code fragment} to present in the system.
 */
@Controller @RequestMapping("ajax/fragments/{userName}/wallet")
public class AjaxFragmentsUserWalletController {

    @GetMapping public String getWalletFragment(@PathVariable("userName") String userName, Model model) {
        User user = Engine.findUserByNameForced(userName);
        model.addAttribute("user", user);
        return "fragments/user/wallet/user-wallet-fragment :: walletFragment";
    }

}
