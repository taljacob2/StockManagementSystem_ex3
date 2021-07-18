package user.wallet.operation;

import user.wallet.operation.type.OperationType;

public class Operation {

    private OperationType operationType;

    private String timeStamp;

    private long totalPricePeriod;

    private long cashBeforeOperation;

    private long cashAfterOperation;

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

    public long getCashBeforeOperation() {
        return cashBeforeOperation;
    }

    public void setCashBeforeOperation(long cashBeforeOperation) {
        this.cashBeforeOperation = cashBeforeOperation;
    }

    public long getCashAfterOperation() {
        return cashAfterOperation;
    }

    public void setCashAfterOperation(long cashAfterOperation) {
        this.cashAfterOperation = cashAfterOperation;
    }
}
