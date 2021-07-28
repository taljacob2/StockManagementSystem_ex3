package com.team.shared.engine.data.user.wallet.operation;

import com.team.shared.engine.data.user.wallet.operation.type.OperationType;

public class Operation {

    private OperationType operationType;

    private String timeStamp;

    private long totalPricePeriod;
    private long balanceBeforeOperation;
    private long balanceAfterOperation;

    @Override public String toString() {
        return "Operation{" + "operationType=" + operationType +
                ", timeStamp='" + timeStamp + '\'' + ", totalPricePeriod=" +
                totalPricePeriod + ", cashBeforeOperation=" +
                balanceBeforeOperation + ", cashAfterOperation=" +
                balanceAfterOperation + '}';
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTotalPricePeriod() {
        return totalPricePeriod;
    }

    public void setTotalPricePeriod(long totalPricePeriod) {
        this.totalPricePeriod = totalPricePeriod;
    }

    public long getBalanceBeforeOperation() {
        return balanceBeforeOperation;
    }

    public void setBalanceBeforeOperation(long balanceBeforeOperation) {
        this.balanceBeforeOperation = balanceBeforeOperation;
    }

    public long getBalanceAfterOperation() {
        return balanceAfterOperation;
    }

    public void setBalanceAfterOperation(long balanceAfterOperation) {
        this.balanceAfterOperation = balanceAfterOperation;
    }
}
