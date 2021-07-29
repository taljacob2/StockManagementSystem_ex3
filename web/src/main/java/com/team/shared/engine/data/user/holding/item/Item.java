package com.team.shared.engine.data.user.holding.item;

import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.data.xjc.generated.RseItem;
import com.team.shared.engine.engine.Engine;
import com.team.shared.model.notification.Notification;
import com.team.shared.model.notification.type.NotificationType;
import com.team.ui.currency.Currency;
import lombok.SneakyThrows;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Objects;

/**
 * This {@code class} represents a Buy or Sell Order.
 *
 * @version 1.0
 */
@XmlRootElement(name = "rse-item") @XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    @XmlAttribute(name = "symbol", required = true) private String symbol;

    @XmlAttribute(name = "quantity", required = true) private long quantity;


    /**
     * Must have a Default Constructor for {@code JAXBContext} <tt>.xml</tt>
     * load and save.
     */
    public Item() {}

    public Item(String symbol, long quantity) {
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public Item(RseItem rseItem) {
        this.symbol = rseItem.getSymbol();
        this.quantity = rseItem.getQuantity();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override public String toString() {
        Stock stock = getStock();
        return "Item{" + "symbol='" + symbol + '\'' + ", quantity=" + quantity +
                ", price=" + Currency.numberFormat.format(stock.getPrice()) +
                '}';

    }

    @Override public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Item item = (Item) o;
        return quantity == item.quantity && Objects.equals(symbol, item.symbol);
    }

    @Override public int hashCode() {
        return Objects.hash(symbol, quantity);
    }

    @SneakyThrows public Stock getStock() {
        try {
            return Engine.getStockBySymbol(symbol);
        } catch (IOException e) {

            // MessagePrint.println(MessagePrint.Stream.OUT, e.getMessage());
            Engine.getUsers().getCollection().forEach(user -> {

                // Notify all users:
                user.getNotifications().addNotification(
                        new Notification(NotificationType.ERROR,
                                "Error While Unmarshalling", e.getMessage()));
            });

            // USE ENGINE BACKUP:
            Engine.useBackup();
            e.printStackTrace();
        }
        return null;
    }
}
