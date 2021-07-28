package shared.dto;

import engine.collection.list.SortableLinkedList;
import lombok.Data;
import order.Order;
import transaction.Transaction;

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

}
