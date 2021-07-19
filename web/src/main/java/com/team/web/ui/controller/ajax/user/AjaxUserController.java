package com.team.web.ui.controller.ajax.user;

import com.team.web.shared.dto.UserDTO;
import engine.Engine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import stock.Stocks;
import user.User;

@SessionAttributes({"user"})
@Slf4j @Controller @RequestMapping("user") public class AjaxUserController {

    /**
     * <i>Finds by name</i> the {@link User} that is <i>name</i> is
     * provided. Returns the {@link User#getUserRole()} as a {@code "text"}
     * response.
     *
     * @param userName the userName to find the {@link User} by.
     * @return produces {@code "text"} of {@link User#getUserRole()}.
     */
    @GetMapping(value = "{userName}/role") @ResponseBody
    public String getUserRole(@PathVariable("userName") String userName,
                              Model model) {
        User user = Engine.findUserByNameForced(userName);

        // Additionally, set an attribute of the user's Role:
        model.addAttribute("userRole", user.getUserRole().toString());

        return user.getUserRole().toString();
    }

    /**
     * Get {@link User} to {@code Attribute} "user".
     *
     * @param userName
     * @param model
     */
    @SneakyThrows @GetMapping(value = "get/{userName}") @ResponseBody
    public void getUser (@PathVariable("userName") String userName,
                        Model model) {

        log.info("userName {}", userName); // DEBUG

        User user = Engine.findUserByNameForced(userName);

        log.info("user {}", user); // DEBUG

        log.info("model before {}", model); // DEBUG

        // Additionally, set an attribute of the user's Role:
        model.addAttribute("user", user);

        log.info("model after {}", model); // DEBUG

    }


    @GetMapping("signed") public String signed(Model model) {
        Stocks stocks = Engine.getStocksForced();
        model.addAttribute("stocksList", stocks.getCollection());

        model.addAttribute("signedInUsersList", Engine.getSignedInUsers());

        model.addAttribute("currentUserDTO", new UserDTO());

        return "mainweb/signed";
    }



    @PostMapping(value = "logout", consumes = "text/plain") public @ResponseBody
    void logout(@RequestBody(required = true) String userName) {

        // Remove UserDTO from SignedInUsers List:
        Engine.getSignedInUsers().removeIf(
                userDTO -> userDTO.getName().equalsIgnoreCase(userName));
    }





}


