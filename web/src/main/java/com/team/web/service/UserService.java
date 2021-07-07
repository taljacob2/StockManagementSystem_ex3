package com.team.web.service;

import com.team.web.shared.dto.UserDTO;
import com.team.web.service.impl.UserServiceImpl;

/**
 * An <i>interface</i> that contains other methods for the {@code Service} of
 * handling {@link user.User} transfers through <i>database</i>.
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
}
