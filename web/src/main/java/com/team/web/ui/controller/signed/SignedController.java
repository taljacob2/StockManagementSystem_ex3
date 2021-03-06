package com.team.web.ui.controller.signed;

import com.team.shared.dto.CompanyDTO;
import com.team.shared.dto.UserDTO;
import com.team.shared.dto.WalletBalanceDTO;
import com.team.shared.engine.data.user.User;
import com.team.shared.engine.engine.Engine;
import com.team.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j @Controller @RequestMapping("signed") public class SignedController {

    @GetMapping
    public String returnToSigned(@ModelAttribute("userDTO") UserDTO userDTO) {
        User user = Engine.findUserByNameForced(userDTO.getName());

        String returnString = "redirect:/signed/user";
        if (user.getRole().toString().equalsIgnoreCase("ADMIN")) {
            returnString = "redirect:/signed/admin";
        }
        return returnString;
    }

    @Controller @RequestMapping("signed/user")
    public static class SignedUserController {

        @Autowired UserService userService;

        @GetMapping public String signed() {
            return "mainweb/signed";
        }

        @GetMapping("addBalance")
        public String addBalanceShowForm(Model model) {
            model.addAttribute("walletBalanceDTO", new WalletBalanceDTO());
            return "wallet/add-balance";
        }

        @PostMapping("addBalance") public ModelAndView addBalanceSubmitForm(
                @ModelAttribute("walletBalanceDTO")
                        WalletBalanceDTO walletBalanceDTO) {
            userService.addBalance(walletBalanceDTO);
            return new ModelAndView("redirect:/signed/user");
        }

        @GetMapping("addCompany")
        public String addCompanyShowForm(Model model) {
            model.addAttribute("companyDTO", new CompanyDTO());
            return "company/add-company";
        }

        @PostMapping("addCompany") public ModelAndView addCompanySubmitForm(
                @ModelAttribute("companyDTO") CompanyDTO companyDTO) {
            userService.addCompany(companyDTO);
            return new ModelAndView("redirect:/signed/user");
        }

    }

    @Controller @RequestMapping("signed/admin")
    public static class SignedAdminController {

        @GetMapping public String signed() {
            return "mainweb/signed-admin";
        }

    }

}
