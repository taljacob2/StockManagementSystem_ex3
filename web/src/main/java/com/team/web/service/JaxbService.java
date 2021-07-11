package com.team.web.service;

import user.User;

public interface JaxbService {

    void marshal();

    void unmarshal(User user, String pathToXMLFile);

}
