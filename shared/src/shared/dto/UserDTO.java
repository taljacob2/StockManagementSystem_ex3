package shared.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A {@code class} which is used for data transfers between different layers in
 * the <tt>Spring</tt> framework.
 */
@Data public class UserDTO implements Serializable {
    private static final long serialVersionUID = 5143034332788520792L;

    private String name;
    private String role;
}
