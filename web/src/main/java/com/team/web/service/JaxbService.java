package com.team.web.service;

import user.User;

/**
 * An <i>interface</i> that contains other methods for the {@code Service} of
 * handling {@link user.User} transfers through <i>database</i>.
 *
 * @see com.team.web.service.impl.JaxbServiceImpl
 */
public interface JaxbService {

    void marshal();

    void unmarshal(User user, String pathToXMLFile);

}
