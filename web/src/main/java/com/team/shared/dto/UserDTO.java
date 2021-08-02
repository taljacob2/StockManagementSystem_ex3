package com.team.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A {@code class} which is used for data transfers between different layers in
 * the <tt>Spring</tt> framework.
 */
@AllArgsConstructor @NoArgsConstructor @Data public class UserDTO implements Serializable {
    private static final long serialVersionUID = 5143034332788520792L;

    private String name;
    private String role;
}
