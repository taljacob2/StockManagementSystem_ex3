package com.team.shared.engine.data.user.wallet;

import com.team.shared.engine.data.user.wallet.operation.Operation;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;

@Data public class Wallet implements Serializable {

    private static final long serialVersionUID = -3448152241044160395L;

    private long balance = 0L;
    private LinkedList<Operation> operationsList = new LinkedList<>();

}
