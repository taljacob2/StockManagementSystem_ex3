package com.team.shared.engine.data.user.wallet;

import com.team.shared.engine.data.user.wallet.operation.Operation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wallet implements Serializable {

    private static final long serialVersionUID = -3448152241044160395L;

    private long balance;

    private List<Operation> operationsList = new ArrayList<>();

    @Override public String toString() {
        return "Wallet{" + "cash=" + balance + ", operationsList=" +
                operationsList + '}';
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public List<Operation> getOperationsList() {
        return operationsList;
    }

    public void setOperationsList(List<Operation> operationsList) {
        this.operationsList = operationsList;
    }
}
