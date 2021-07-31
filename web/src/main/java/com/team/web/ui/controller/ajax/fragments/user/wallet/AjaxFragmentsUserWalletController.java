package com.team.web.ui.controller.ajax.fragments.user.wallet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Responding a {@code fragment} to present in the system.
 */
@Controller @RequestMapping("ajax/fragments/wallet")
public class AjaxFragmentsUserWalletController {

    @GetMapping public String getWalletFragment() {
        return "fragments/user/wallet/user-wallet-fragment :: walletFragment";
    }

}
