package com.team.shared.engine.data.stock.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team.shared.engine.data.collection.AddTabsCollection;
import com.team.shared.engine.data.collection.Periodable;
import com.team.shared.engine.data.collection.list.SortableLinkedList;
import com.team.shared.engine.data.order.BuyOrders;
import com.team.shared.engine.data.order.Order;
import com.team.shared.engine.data.order.SellOrders;
import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.data.transaction.Transaction;
import com.team.shared.engine.data.transaction.Transactions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rse-data" + "-base") public class StockDataBase {

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

    @JsonIgnore public BuyOrders getAwaitingBuyOrders() {
        return awaitingBuyOrders;
    }

    @JsonIgnore public void setAwaitingBuyOrders(BuyOrders awaitingBuyOrders) {
        this.awaitingBuyOrders = awaitingBuyOrders;
    }

    @JsonIgnore public SellOrders getAwaitingSellOrders() {
        return awaitingSellOrders;
    }

    @JsonIgnore
    public void setAwaitingSellOrders(SellOrders awaitingSellOrders) {
        this.awaitingSellOrders = awaitingSellOrders;
    }

    @JsonIgnore public Transactions getSuccessfullyFinishedTransactions() {
        return successfullyFinishedTransactions;
    }

    @JsonIgnore public void setSuccessfullyFinishedTransactions(
            Transactions successfullyFinishedTransactions) {
        this.successfullyFinishedTransactions =
                successfullyFinishedTransactions;
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

