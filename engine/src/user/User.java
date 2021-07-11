package user;

import currency.Currency;
import engine.Engine;
import message.print.MessagePrint;
import stock.Stock;
import user.holding.Holdings;
import user.holding.item.Item;

import javax.xml.bind.annotation.*;
import java.io.IOException;
import java.util.Objects;

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
public class User {

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
    @XmlElement(name = "rse-holdings") private Holdings holdings;


    public User(String name, String role) {
        this.name = name;
        holdings = new Holdings();
        this.role = Role.valueOf(role);
    }

    /**
     * Must have a Default Constructor for {@code JAXBContext} <tt>.xml</tt>
     * load and save.
     */
    public User() {
        holdings = new Holdings();
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

        return "User: " + "name='" + name + '\'' + "role=" + role + ": " +
                "[Total Holdings Worth = " +
                Currency.numberFormat.format(totalHoldingsWorth) + "]" + "\n" +
                holdings.getCollection().toString("\t\t\t");
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

    public String getName() {
        return name;
    }

    public void setName(String name) { // TODO: check with schema-XML-builder
        this.name = name;
    }

    /**
     * Defines {@link #role} types.
     */
    public enum Role {USER, ADMIN}

}
