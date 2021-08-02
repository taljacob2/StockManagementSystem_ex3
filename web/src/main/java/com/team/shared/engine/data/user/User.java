package com.team.shared.engine.data.user;

import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.data.user.holding.Holdings;
import com.team.shared.engine.data.user.holding.item.Item;
import com.team.shared.engine.data.user.notification.Notifications;
import com.team.shared.engine.data.user.role.Role;
import com.team.shared.engine.data.user.wallet.Wallet;
import com.team.shared.engine.data.xjc.generated.RseHoldings;
import com.team.shared.engine.engine.Engine;
import com.team.shared.engine.message.print.MessagePrint;
import com.team.shared.model.notification.Notification;
import com.team.ui.currency.Currency;

import javax.xml.bind.annotation.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This {@code class} represents a {@code User} in the system.
 * <p>A {@code User} owns {@link Holdings} of {@link Item}(s)
 * which represent the {@link Stock}(s) he owns and their according {@code
 * quantity}.</p>
 *
 * <p>annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.</p>
 *
 * @version 1.0
 */
@XmlRootElement(name = "rse-user") @XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable, Cloneable {

    private static final long serialVersionUID = -1487798576025361731L;

    /**
     * The {@code name} of the {@code User}.
     */
    @XmlAttribute(name = "name", required = true) private String name;

    /**
     * The {@code role} of the {@code User}.
     */
    @XmlAttribute(name = "role", required = true) private Role role;

    /**
     * Element = {@link Item}.
     */
    @XmlElement(name = "rse-holdings", required = true) private Holdings
            holdings;

    @XmlElement(name = "rse-wallet", required = true) private Wallet wallet =
            new Wallet();

    /**
     * {@link Collection} of all {@link Notification}s that this {@code User}
     * has received.
     * <ul>
     *     <li> {@code Key} = <i>{@link Notification}</i>.</li>
     *     <li> {@code Value} = <i>{@link Boolean} is shown already</i>.</li>
     * </ul>
     */
    @XmlElement(name = "rse-notifications") private Notifications
            notifications = new Notifications();

    public User(String name, Role role) {
        this.name = name;
        holdings = new Holdings();
        this.role = role;
    }

    /**
     * Must have a Default Constructor for {@code JAXBContext} <tt>.xml</tt>
     * load and save.
     */
    public User() {
        holdings = new Holdings();
    }

    public Notifications getNotifications() {
        return notifications;
    }

    public void setNotifications(Notifications notifications) {
        this.notifications = notifications;
    }

    @Override public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(holdings, user.holdings);
    }

    @Override public int hashCode() {
        return Objects.hash(name, holdings);
    }

    @Override public String toString() {

        // calculate total holdings worth:
        long totalHoldingsWorth = calculateTotalHoldingsWorth();

        return "User: " + "name='" + name + '\'' + ", userRole=" + role + ": " +
                "[Total Holdings Worth = " +
                Currency.decimalFormat.format(totalHoldingsWorth) + "]" + "\n" +
                holdings.getCollection()
                        .toString(/* Note: no "\t\t\t" tabs here. */) + ", "+ wallet +", " + notifications;

    }

    public long calculateTotalHoldingsWorth() {

        // calculate total holdings worth:
        long totalHoldingsWorth = 0L;
        for (Item item : holdings.getCollection()) {
            try {
                Stock stock = Engine.getStockBySymbol(item.getSymbol());
                totalHoldingsWorth += (item.getQuantity() * stock.getPrice());
            } catch (IOException e) {

                // Note: this exception should not happen thanks to the initial check of users.
                MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
            }
        }
        return totalHoldingsWorth;
    }

    public Holdings getHoldings() {
        return holdings;
    }

    public void setHoldings(Holdings holdings) {
        this.holdings = holdings;
    }

    public void setHoldings(RseHoldings rseHoldings) {
        this.holdings = new Holdings(rseHoldings);
    }

    public void addHoldings(RseHoldings rseHoldings) {
        if (holdings == null) {
            holdings = new Holdings();
        }
        if (this.holdings.getCollection() == null) {
            this.holdings.setCollection(new ArrayList<>());
        }

        /*
         * Converts all RseItems to Items, then collect them to a list,
         * and add all.
         */
        List<Item> listOfNewItemsProvided =
                rseHoldings.getRseItem().stream().map(Item::new)
                        .collect(Collectors.toList());

        List<String> listOfSymbolsAlreadyInTheSystem = holdings.
                getCollection().stream().map(Item::getSymbol)
                .collect(Collectors.toList());


        listOfNewItemsProvided.forEach(newItem -> {
            if (!listOfSymbolsAlreadyInTheSystem
                    .contains(newItem.getSymbol())) {

                /*
                 * If the newStock is already in the system, skip it.
                 * Add only the stocks that their symbols are not yet in
                 * the system.
                 */
                holdings.getCollection().add(newItem);
            }
        });
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { // TODO: check with schema-XML-builder
        this.name = name;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

}
