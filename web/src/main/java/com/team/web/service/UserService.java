package com.team.web.service;

import com.team.shared.engine.data.user.User;
import com.team.web.service.impl.UserServiceImpl;
import com.team.shared.dto.UserDTO;
import com.team.shared.engine.engine.Engine;

/**
 * An <i>interface</i> that contains other methods for the {@code Service} of
 * handling {@link User} transfers through <i>database</i>.
 *
 * @see UserServiceImpl
 */
public interface UserService {

    /**
     * This method creates a new {@code User} from the given {@code UserDTO}.
     *
     * @param userDTO the {@link UserDTO} to create a new {@code User} from.
     * @return the {@link UserDTO} of the newly created {@code User}.
     */
    UserDTO createUser(UserDTO userDTO);

    /**
     * This method inserts a new {@link UserDTO} to the {@link
     * Engine#getSignedInUsers()}.
     *
     * @param userDTOThatHasOnlyNameInitialized a {@link UserDTO} that has only
     *                                          the {@link UserDTO#getName()}
     *                                          initialized.
     */
    void insertToSignedInUsersList(
            UserDTO userDTOThatHasOnlyNameInitialized);
}
