package com.team.web.ui.controller.user;

import com.team.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/* TODO: this is a test controller. Remove this! */
@RestController @RequestMapping("users") // http:localhost:8080/web/users
public class UserController {

    @Autowired UserService userService;

    @GetMapping public String getUser() {
        return "get user was called";
    }


    // @PostMapping public UserRest createUser(
    //         @RequestBody UsesDetailRequestModel userDetails) {
    //
    //     // Create a DTO to transfer the data given from the client.
    //     UserDTO requestUserDTO = new UserDTO();
    //
    //     // Copy the data from the given 'userDetails' to the DTO.
    //     BeanUtils.copyProperties(userDetails, requestUserDTO);
    //
    //     /*
    //      * Create a User Entity from the data in the DTO, and store its data
    //      * in a newly created DTO.
    //      */
    //     UserDTO createdUserDTO = userService.createUser(requestUserDTO);
    //
    //     // Create a user response.
    //     UserRest returnValue = new UserRest();
    //
    //     BeanUtils.copyProperties(createdUserDTO, returnValue);
    //
    //     return returnValue;
    // }

    @PutMapping public String updateUser() {
        return "update user was called";
    }

    @DeleteMapping public String deleteUser() {
        return "delete user was called";
    }

}
