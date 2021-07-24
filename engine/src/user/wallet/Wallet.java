package user.wallet;

import user.wallet.operation.Operation;

import java.util.ArrayList;
import java.util.List;

public class Wallet {

    private long cash;

    private List<Operation> operationsList = new ArrayList<>();

    @Override public String toString() {
        return "Wallet{" + "cash=" + cash + ", operationsList=" +
                operationsList + '}';
    }

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }

    public List<Operation> getOperationsList() {
        return operationsList;
    }

    public void setOperationsList(List<Operation> operationsList) {
        this.operationsList = operationsList;
    }
}
