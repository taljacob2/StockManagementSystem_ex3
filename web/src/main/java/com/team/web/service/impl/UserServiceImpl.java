package com.team.web.service.impl;

import com.team.web.service.UserService;
import com.team.web.shared.dto.UserDTO;
import engine.Engine;
import org.springframework.stereotype.Service;
import user.User;

/**
 * A {@code Service} which serves for handling {@link User} performances to the
 * <i>database</i> (Which on this project, stored in {@link
 * Engine#getUsers()}).
 * <p>
 * This service is ready for future use of <i>database</i> implementations.
 * </p>
 */
@Service public class UserServiceImpl implements UserService {

    /**
     * Creates a new {@link user.User} from the given {@link UserDTO}, and
     * stores it in the {@link user.Users} {@link java.util.Collection} inside
     * the {@link engine.Engine}.
     *
     * @param userDTO the {@link UserDTO} to create a new {@code User} from.
     * @return the response {@link UserDTO} extracted from the newly created
     * {@link User}.
     */
    @Override public UserDTO createUser(UserDTO userDTO) {

        // Create a new User from the given 'userDTO':
        User user = new User(userDTO.getName());

        /*
         * Check if the user is already exists, before inserting it to the
         * database.
         */
        if (Engine.findUserByNameForced(user) != null) {
            throw new RuntimeException("User Already Exists"); // error 500
        }

        // Add the new User to the Engine's database of Users:
        Engine.insertUserForced(user);

        // Create a response userDTO from the newly made User:
        UserDTO returnValue = new UserDTO();
        returnValue.setName(user.getName());
        return returnValue;
    }
}
