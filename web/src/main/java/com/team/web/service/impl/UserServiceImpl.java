package com.team.web.service.impl;

import com.team.shared.dto.UserDTO;
import com.team.shared.engine.data.user.User;
import com.team.shared.engine.data.user.role.Role;
import com.team.shared.engine.engine.Engine;
import com.team.web.service.UserService;
import org.springframework.stereotype.Service;

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
     * the {@link Engine}.
     *
     * @param userDTO the {@link UserDTO} to create a new {@code User} from.
     * @return the response {@link UserDTO} extracted from the newly created
     * {@link User}.
     */
    @Override public UserDTO createUser(UserDTO userDTO) {

        // Set the role to upper-case:
        userDTO.setRole(userDTO.getRole().toUpperCase());

        // Create a new User from the given 'userDTO':
        User user = new User(userDTO.getName(),
                Role.valueOf(userDTO.getRole()));

        /*
         * Check if the user is already exists, before inserting it to the
         * database.
         */
        if (Engine.findUserByNameForced(user.getName()) != null) {
            throw new RuntimeException("User Already Exists"); // error 500
        }

        // Add the new User to the Engine's database of Users:
        Engine.insertUserForced(user);

        // Create a response userDTO from the newly made User:
        UserDTO returnValue = new UserDTO();
        returnValue.setName(user.getName());
        return returnValue;
    }

    /**
     * <ul>
     *     <li>Gets the {@code userName} of the signed in {@code User} by a
     *     {@link UserDTO#getName()}.</li>
     *     <li>Finds the according {@link User} <i>found by name</i> of this
     *     {@code userName}.
     *     <li>Inserts the found {@link User} to the
     *     {@link Engine#getSignedInUsers()} list.</li>
     * </ul>
     *
     * @param userDTOThatHasOnlyNameInitialized a {@link UserDTO} that has only
     *                                          the {@link UserDTO#getName()}
     */
    @Override public void insertToSignedInUsersList(
            UserDTO userDTOThatHasOnlyNameInitialized) {

        // Check if the signedInUsersList already contains this username:
        for (UserDTO userDTO : Engine.getSignedInUsers()) {
            if (userDTO.getName().equalsIgnoreCase(
                    userDTOThatHasOnlyNameInitialized.getName())) {
                /*
                 * User is already signed-in, So do not add it to the
                 * signedInUsersList, and quit.
                 */
                return;
            }
        }

        // Get the User from the given "requestUserDTO.getName()":
        User user = Engine.findUserByNameForced(
                userDTOThatHasOnlyNameInitialized.getName());

        // Set the requestUserDTO with its found Role.
        userDTOThatHasOnlyNameInitialized
                .setRole(user.getRole().toString());

        // Add the UserDTO to the Engine's signedInUsers List:
        Engine.getSignedInUsers().add(userDTOThatHasOnlyNameInitialized);
    }
}
