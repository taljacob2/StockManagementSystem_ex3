package com.team.shared.engine.data.stock.database;

import com.team.shared.engine.data.collection.AddTabsCollection;
import com.team.shared.engine.data.collection.Periodable;
import com.team.shared.engine.data.collection.list.SortableLinkedList;
import com.team.shared.engine.data.order.BuyOrders;
import com.team.shared.engine.data.order.Order;
import com.team.shared.engine.data.order.SellOrders;
import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.data.transaction.Transaction;
import com.team.shared.engine.data.transaction.Transactions;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

/**
 * This {@code class} manages all the {@link Order}(s) and {@link
 * Transaction}(s) of a specific {@link Stock}.
 * <p>There are 3 main {@code Collection}s:</p>
 * <ul>
 *     <li>{@link #awaitingBuyOrders}.</li>
 *     <li>{@link #awaitingSellOrders}.</li>
 *     <li>{@link #successfullyFinishedTransactions}.</li>
 * </ul>
 * <p>
 *   Constructor:
 *   <p>initializes all fields.</p>
 *   <p>Note:</p>
 *   <ul>
 *       <li> <i>Must</i> initialize the fields with their {@code
 *            Constructor}s.</li>
 *       <li>{@link SortableLinkedList} {@code Overrides} each {@code toString()}.</li>
 *   </ul>
 * </p>
 *   @see AddTabsCollection
 *
 * @version 2.0
 */
@Data @XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rse-data-base") public class StockDataBase
        implements Serializable, Cloneable {

    private static final long serialVersionUID = 7928162182907677599L;

    /**
     * Element = {@link Order}.
     */
    @XmlElement(name = "rse-buy-orders") private BuyOrders awaitingBuyOrders =
            new BuyOrders();

    /**
     * Element = {@link Order}.
     */
    @XmlElement(name = "rse-sell-orders") private SellOrders
            awaitingSellOrders = new SellOrders();

    /**
     * Element = {@link Transaction}.
     */
    @XmlElement(name = "rse-successfully-finished-transactions")
    private Transactions successfullyFinishedTransactions = new Transactions();

    public StockDataBase() {}

    public StockDataBase clone() throws CloneNotSupportedException {
        StockDataBase clone = (StockDataBase) super.clone();
        clone.setAwaitingBuyOrders(this.awaitingBuyOrders.clone());
        clone.setAwaitingSellOrders(this.awaitingSellOrders.clone());
        clone.setSuccessfullyFinishedTransactions(
                (Transactions) this.successfullyFinishedTransactions.clone());
        return clone;
    }


    /**
     * This method calculates the {@code Total-Collection-Period} of all the
     * {@code Elements} in the {@link Collection} and returns it.
     *
     * @param collection is the {@link Collection} to calculate its
     *                   <i>period</i>.
     * @param <E>        is the element in the {@code collection} given.
     * @return The {@code Total-Collection-Period}.
     */
    public <E extends Periodable> long getTotalPeriod(
            Collection<E> collection) {
        long totalPeriod = 0L;
        for (E i : collection) {
            totalPeriod += i.getPeriod();
        }
        return totalPeriod;
    }

}

