package com.team.web.shared.dto;

import java.io.Serializable;

/**
 * A {@code class} which is used for data transfers between different layers in
 * the <tt>Spring</tt> framework.
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 5143034332788520792L;

    private String name;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
