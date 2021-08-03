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

    /**
     * Uploads a <tt>.xml</tt> file to the holdings of a given {@link User}.
     *
     * @param user          the {@link User} who uploads the <tt>.xml</tt>
     *                      file.
     * @param pathToXMLFile the <i>path</i> to the <tt>.xml</tt> file.
     */
    void unmarshal(User user, String pathToXMLFile);

}
