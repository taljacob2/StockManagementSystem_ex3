package com.team.shared.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A {@code class} which is used for data transfers between different layers in
 * the <tt>Spring</tt> framework.
 */
@Data public class WalletBalanceDTO implements Serializable {

    private static final long serialVersionUID = 1445026713033674026L;

    private String userName;
    private long walletBalanceToAdd;

}
