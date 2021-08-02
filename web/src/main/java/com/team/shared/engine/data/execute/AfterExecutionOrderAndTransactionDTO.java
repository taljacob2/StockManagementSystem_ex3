package com.team.shared.engine.data.execute;


import com.team.shared.engine.data.collection.list.SortableLinkedList;
import com.team.shared.engine.data.order.Order;
import com.team.shared.engine.data.transaction.Transaction;
import lombok.Data;

import java.util.Iterator;
import java.util.List;

/**
 * Contains all {@link Order}s and {@link Transaction}s made after
 * <i>order-execution.</i>
 */
@Data public class AfterExecutionOrderAndTransactionDTO {

    /**
     * {@link java.util.List} of all {@link Order}s remained after
     * <i>order-execution.</i>
     */
    private SortableLinkedList<Order> remainderOrders =
            new SortableLinkedList<>();

    /**
     * {@link java.util.List} of all {@link Transaction}s made after
     * <i>order-execution.</i>
     */
    private SortableLinkedList<Transaction> transactions =
            new SortableLinkedList<>();

    private void removeRemaindersFor(List<Order> dataBaseOrders){
        for (Iterator<Order> it = dataBaseOrders.iterator();
             it.hasNext(); ) {
            Order dataBaseOrder = it.next();
            for (Order remainedOrder : remainderOrders) {
                if (dataBaseOrder == remainedOrder) {
                    it.remove();
                    break;
                }
            }
        }
    }

    // TODO: remove
    // public void removeRemainders(StockDataBase stockDataBase) {
    //     if (remainderOrders.size() > 0) {
    //         if (remainderOrders.getFirst().getOrderDirection() ==
    //                 OrderDirection.BUY) {
    //
    //             // The remainderOrders is all BUY orders:
    //             List<Order> buyOrders =
    //                     stockDataBase.getAwaitingBuyOrders().getCollection();
    //             removeRemaindersFor(buyOrders);
    //         } else if (remainderOrders.getFirst().getOrderDirection() ==
    //                 OrderDirection.SELL) {
    //
    //             // The remainderOrders is all SELL orders:
    //             List<Order> sellOrders =
    //                     stockDataBase.getAwaitingSellOrders().getCollection();
    //             removeRemaindersFor(sellOrders);
    //         }
    //     }
    // }
}
