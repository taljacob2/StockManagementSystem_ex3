package com.team.shared.engine.message.builder.out;

import com.team.shared.engine.message.Message;
import com.team.shared.engine.message.builder.BuildMessage;
import com.team.shared.engine.message.print.Log;
import com.team.shared.engine.message.print.MessagePrint;
import com.team.shared.engine.message.builder.err.BuildError;
import shared.engine.data.order.Order;
import shared.engine.data.transaction.Transaction;

/**
 * This {@code class} is a sub-class in the message building process.
 * <p>Represents a part of an {@code Output} message.</p>
 *
 * @version 1.0
 * @see BuildMessage
 * @see BuildOutput
 */
public class BuildOutput_StockDataBase extends BuildOutput {

    public String printEmpty(TypeOfCollection typeOfCollection) {

        // append the message.
        if (typeOfCollection ==
                TypeOfCollection.SUCCESSFULLY_FINISHED_TRANSACTIONS) {
            stringBuilder
                    .append("There have been no Transactions with this stock yet.\n ");
        } else if (typeOfCollection == TypeOfCollection.AWAITING_BUY_ORDERS) {
            stringBuilder.append("There are no " + "awaiting 'Buy' Orders" +
                    " for this stock right now.\n");
        } else if (typeOfCollection == TypeOfCollection.AWAITING_SELL_ORDERS) {
            stringBuilder.append("There are no " + "awaiting 'Sell' Orders" +
                    " for this stock right now.\n");
        }

        // return the String.
        return getMessage();
    }

    /**
     * @param object {@link Transaction} or {@link Order}. the method detects
     *               the {@code Object} given.
     * @return message of a successful addition.
     */
    public String newSuccessAdd(Object object) {

        // append the message.
        if (object instanceof Transaction) {
            Transaction transaction = (Transaction) object;
            stringBuilder
                    .append(Message.Out.Input.success("Transaction creation"))
                    .append(":\n\t").append(transaction.toString());
        } else if (object instanceof Order) {
            Order order = (Order) object;

            stringBuilder.append(Message.Out.Input.success("Order creation"))
                    .append(":\n\t").append(order.toString());
        } else {
            MessagePrint.println(MessagePrint.Stream.ERR,
                    new BuildError().getMessage() +
                            "Problem when adding to the data-base.");
        }

        // Add a newLine to the Log-View:
        Log.getMessageLog().append("\n");

        // return the String.
        return getMessage();
    }

    public String printOrderPerformedInItsEntirety() {

        // append the message.

        stringBuilder
                .append("The Order was successfully performed in its entirety, and was removed.");

        // return the String.
        return getMessage();
    }

    /**
     * Defines the message the method {@link #printEmpty(TypeOfCollection)}
     * prints.
     */
    public enum TypeOfCollection {
        AWAITING_BUY_ORDERS, AWAITING_SELL_ORDERS,
        SUCCESSFULLY_FINISHED_TRANSACTIONS
    }

}
