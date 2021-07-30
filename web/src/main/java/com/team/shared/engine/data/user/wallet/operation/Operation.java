package com.team.shared.engine.data.user.wallet.operation;

import com.team.shared.engine.data.user.wallet.Wallet;
import com.team.shared.engine.data.user.wallet.operation.type.OperationType;
import com.team.shared.engine.timestamp.TimeStamp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data @AllArgsConstructor public class Operation implements Serializable {

    private static final long serialVersionUID = -1568759192415027393L;

    private OperationType operationType;

    /**
     * Only in case of <code> {@link #operationType} == ({@link
     * OperationType#BUY} || {@link OperationType#SELL}) </code>
     */
    private String stockSymbol = null;
    private String timeStamp;
    private long balanceTransferred;
    private long balanceBeforeOperation;
    private long balanceAfterOperation;

    /**
     * Constructor <i>without</i> {@link #stockSymbol}. Only in case of <code>
     * {@link #operationType} == ({@link OperationType#BUY} || {@link
     * OperationType#SELL}) </code>
     *
     * @param operationType          <code>
     *                               {@link #operationType} == ({@link
     *                               OperationType#BUY} || {@link OperationType#SELL})
     *                               </code>
     * @param timeStamp              the current {@link TimeStamp#getTimeStamp()}.
     * @param balanceTransferred     the {@link Wallet#getBalance()} transferred
     *                               in this {@code Operation}.
     * @param balanceBeforeOperation the {@link Wallet#getBalance()}
     *                               <i>before</i> this {@code Operation}.
     * @param balanceAfterOperation  the {@link Wallet#getBalance()} <i>after
     *                               </i> this {@code Operation}.
     */
    public Operation(OperationType operationType, String timeStamp,
                     long balanceTransferred, long balanceBeforeOperation,
                     long balanceAfterOperation) {
        this.operationType = operationType;
        this.timeStamp = timeStamp;
        this.balanceTransferred = balanceTransferred;
        this.balanceBeforeOperation = balanceBeforeOperation;
        this.balanceAfterOperation = balanceAfterOperation;
    }
}
