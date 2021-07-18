package com.team.web.ui.controller.signed;

import com.team.web.shared.dto.UserDTO;
import engine.Engine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import stock.Stocks;

@Controller @RequestMapping("signed") public class SignedController {

    @GetMapping public String signed(Model model) {
        Stocks stocks = Engine.getStocksForced();
        model.addAttribute("stocksList", stocks.getCollection());

        model.addAttribute("signedInUsersList", Engine.getSignedInUsers());

        model.addAttribute("currentUserDTO", new UserDTO());

        return "mainweb/signed";
    }

    @GetMapping("/admin") public String signedAdmin(Model model) {
        Stocks stocks = Engine.getStocksForced();
        model.addAttribute("stocksList", stocks.getCollection());

        model.addAttribute("signedInUsersList", Engine.getSignedInUsers());

        model.addAttribute("currentUserDTO", new UserDTO());

        return "mainweb/signed-admin";
    }

}
