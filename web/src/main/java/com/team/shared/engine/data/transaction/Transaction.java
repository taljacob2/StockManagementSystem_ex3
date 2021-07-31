package com.team.shared.engine.data.transaction;

import com.team.shared.engine.data.collection.Periodable;
import com.team.shared.engine.data.order.Order;
import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.data.user.User;
import com.team.shared.engine.data.user.holding.item.Item;
import com.team.shared.engine.data.user.wallet.Wallet;
import com.team.shared.engine.data.user.wallet.operation.Operation;
import com.team.shared.engine.data.user.wallet.operation.type.OperationType;
import com.team.shared.engine.timestamp.TimeStamp;
import com.team.ui.currency.Currency;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This {@code class} represents a {@code Transaction} of {@link Stock}s.
 * <p>A {@code Transaction} occurs when two {@link Order}(s) happen to
 * match successfully:</p>
 *
 * <p>A '{@code Buy}' {@link Order} and a '{@code Sell}' {@link
 * Order}.</p>
 *
 * <p>annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.</p>
 *
 * @version 1.0
 */
@XmlRootElement(name = "rse-transaction") @XmlAccessorType(XmlAccessType.FIELD)
public class Transaction
        implements Comparable<Transaction>, Periodable, Serializable {

    private static final long serialVersionUID = 9064387369573452805L;

    /**
     * The {@code TimeStamp} of the {@code Transaction}'s execution.
     */
    @XmlElement(name = "time-stamp", required = true) private String timeStamp;

    /**
     * The quantity of the {@link Stock}s sold in the {@code Transaction}.
     */
    @XmlElement(name = "quantity", required = true) private long quantity;

    /**
     * Price of each sold {@link Stock}.
     */
    @XmlElement(name = "price", required = true) private long price;

    /**
     * The {@link User} who <i>sold</i> a {@link Stock} in the current {@code
     * Transaction}.
     */
    @XmlElement(name = "buying-user") private User buyingUser;

    /**
     * The {@link User} who <i>bought</i> a {@link Stock} in the current {@code
     * Transaction}.
     */
    @XmlElement(name = "selling-user") private User sellingUser;

    /**
     * The <i>serial-time</i> that the {@code Transaction} was created, when
     * viewing in a {@code AfterExecutionSummary} {@code Pane}.
     */
    private long serialTime;

    public Transaction(Stock stock, String timeStamp, long quantity, long price,
                       User buyingUser, User sellingUser, long serialTime) {
        this.timeStamp = timeStamp;
        this.quantity = quantity;
        this.price = price;
        this.buyingUser = buyingUser;
        this.sellingUser = sellingUser;
        this.serialTime = serialTime;

        // forces update of the Stock's price:
        stock.setPrice(price);

        // forces update of the Stock's graph:
        stock.getStockGraphSeries().getCollection()
                .add(new AbstractMap.SimpleEntry<String, Long>(timeStamp,
                        stock.getPrice()));
    }

    /**
     * Must have a Default Constructor for {@code JAXBContext} <tt>.xml</tt>
     * load and save.
     */
    public Transaction() {}

    public long getSerialTime() {
        return serialTime;
    }

    public void setSerialTime(long serialTime) {
        this.serialTime = serialTime;
    }

    public User getBuyingUser() {
        return buyingUser;
    }

    public void setBuyingUser(User buyingUser) {
        this.buyingUser = buyingUser;
    }

    public User getSellingUser() {
        return sellingUser;
    }

    public void setSellingUser(User sellingUser) {
        this.sellingUser = sellingUser;
    }

    @Override public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Transaction that = (Transaction) o;
        return quantity == that.quantity && price == that.price &&
                Objects.equals(timeStamp, that.timeStamp);
    }

    @Override public int hashCode() {
        return Objects.hash(timeStamp, quantity, price);
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override public String toString() {
        return "Transaction{" + "timeStamp='" + timeStamp + '\'' +
                ", quantity=" + quantity + ", price=" +
                Currency.decimalFormat.format(price) + ", transactionPeriod=" +
                Currency.decimalFormat.format(getPeriod()) + ", buyingUserName" +
                "=" + buyingUser.getName() + ", sellingUserName=" +
                sellingUser.getName() + '}';
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    /**
     * This method compares by the {@link #timeStamp} values of the {@code
     * Transaction}s.
     *
     * @param o the <i> other </i> {@code Transaction} to be compared to.
     * @return an {@code int} indicates if {@code this} {@code Transaction} is
     * less, equals, or greater than the <i> other </i> {@code Transaction}
     * given.
     */
    @Override public int compareTo(Transaction o) {

        // compare by 'timeStamp': the first is the most recent.
        int result = o.getTimeStamp().compareTo(this.getTimeStamp());
        if (result == 0) {

            // if 'timeStamps' are equal, insert the most recent on top.
            return -1;
        } else { return result; }
    }

    /**
     * The total <i>price worth</i> of this {@code Transaction} is: the
     * <tt>{@link #quantity}</tt> of the {@link Stock}s times the
     * {@link #price} of each {@link Stock} in the {@code Transaction}.
     *
     * @return {@code Transaction-Worth} <i>price</i> = <tt>Period</tt>.
     */
    @Override public long getPeriod() {
        return quantity * price;
    }

    public String getPriceCurrency() {
        return Currency.decimalFormat.format(price);
    }

    /**
     * Transfers a {@link Stock} from the {@link #sellingUser} to the {@link
     * #buyingUser}, as transferring their {@link com.team.shared.engine.data.user.holding.Holdings}
     * as updating their {@link Wallet}s accordingly.
     *
     * @param stockSymbol the {@link Stock#getSymbol()} of the {@link Stock}
     *                    being transferred.
     */
    public void transfer(String stockSymbol) {
        transferBalance(stockSymbol);
        transferHoldings(stockSymbol);
    }

    /**
     * Transfer the {@code Transaction}'s worth from the {@link
     * Wallet#getBalance()} of the {@code buyingUser} to the {@code
     * sellingUser}.
     *
     * @param stockSymbol the {@link Stock#getSymbol()} of the {@link Stock}
     *                    being transferred.
     */
    private void transferBalance(String stockSymbol) {

        // Get price and Wallets:
        long priceToTransfer = this.getPrice();
        Wallet buyingUserWallet = buyingUser.getWallet();
        Wallet sellingUserWallet = sellingUser.getWallet();

        // Get balance:
        long buyingUserWalletBalanceBeforeOperation =
                buyingUserWallet.getBalance();
        long buyingUserWalletBalanceAfterOperation =
                buyingUserWallet.getBalance() - priceToTransfer;
        long sellingUserWalletBalanceBeforeOperation =
                sellingUserWallet.getBalance();
        long sellingUserWalletBalanceAfterOperation =
                sellingUserWallet.getBalance() + priceToTransfer;

        // Transfer balance:
        createOperationsToTransferBalance(stockSymbol, priceToTransfer,
                buyingUserWallet, sellingUserWallet,
                buyingUserWalletBalanceBeforeOperation,
                buyingUserWalletBalanceAfterOperation,
                sellingUserWalletBalanceBeforeOperation,
                sellingUserWalletBalanceAfterOperation);
    }

    private void createOperationsToTransferBalance(String stockSymbol,
                                                   long priceToTransfer,
                                                   Wallet buyingUserWallet,
                                                   Wallet sellingUserWallet,
                                                   long buyingUserWalletBalanceBeforeOperation,
                                                   long buyingUserWalletBalanceAfterOperation,
                                                   long sellingUserWalletBalanceBeforeOperation,
                                                   long sellingUserWalletBalanceAfterOperation) {

        // Transfer balance:
        buyingUserWallet.setBalance(buyingUserWalletBalanceAfterOperation);
        sellingUserWallet.setBalance(sellingUserWalletBalanceAfterOperation);

        // Log the Operations to Wallets:
        String timeStamp = TimeStamp.getTimeStamp();

        buyingUserWallet.getOperationsList().addFirst(
                new Operation(OperationType.BUY, stockSymbol, timeStamp,
                        priceToTransfer, buyingUserWalletBalanceBeforeOperation,
                        buyingUserWalletBalanceAfterOperation));

        sellingUserWallet.getOperationsList().addFirst(
                new Operation(OperationType.SELL, stockSymbol, timeStamp,
                        priceToTransfer,
                        sellingUserWalletBalanceBeforeOperation,
                        sellingUserWalletBalanceAfterOperation));
    }

    private void transferHoldings(String stockSymbol) {
        List<Item> buyingUserItemsList =
                buyingUser.getHoldings().getCollection();
        List<Item> sellingUserItemsList =
                sellingUser.getHoldings().getCollection();

        // Modify the referenced object. Note: may contain null:
        Optional<Item> buyingUserOptionalItemOfThisStock =
                buyingUserItemsList.stream().filter(item -> item.getSymbol()
                        .equalsIgnoreCase(stockSymbol)).findFirst();
        buyingUserOptionalItemOfThisStock.ifPresent(item -> {
            item.setQuantity(item.getQuantity() + this.quantity);
        });
        if (!buyingUserOptionalItemOfThisStock.isPresent()) {

            // Add item to list:
            buyingUserItemsList.add(new Item(stockSymbol, this.quantity));
        }

        // Modify the referenced object. Note: may contain null:
        Optional<Item> sellingUserOptionalItemOfThisStock =
                sellingUserItemsList.stream().filter(item -> item.getSymbol()
                        .equalsIgnoreCase(stockSymbol)).findFirst();
        sellingUserOptionalItemOfThisStock.ifPresent(item -> {
            item.setQuantity(item.getQuantity() - this.quantity);
            if (item.getQuantity() == 0) {

                // Remove item from list:
                sellingUserItemsList.remove(item);
            }
        });
    }

}
