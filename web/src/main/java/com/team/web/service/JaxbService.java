package com.team.web.service;


import com.team.shared.engine.data.user.User;

/**
 * An <i>interface</i> that contains other methods for the {@code Service} of
 * handling {@link User} transfers through <i>database</i>.
 *
 * @see com.team.web.service.impl.JaxbServiceImpl
 */
public interface JaxbService {

    void marshal(String stringPathOfXML);

    void unmarshal(User user, String pathToXMLFile);

}
