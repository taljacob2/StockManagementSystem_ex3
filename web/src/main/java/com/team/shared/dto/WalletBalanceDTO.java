package com.team.shared.dto;

import com.team.shared.engine.data.user.User;
import lombok.Data;

import java.io.Serializable;

/**
 * A {@code class} which is used for data transfers between different layers in
 * the <tt>Spring</tt> framework.
 */
@Data public class WalletBalanceDTO implements Serializable {

    private static final long serialVersionUID = 1445026713033674026L;

    /**
     * The requesting {@link User#getName()}.
     */
    private String userName;
    private long walletBalanceToAdd;

}
