package com.team.shared.engine.data.stock;

import com.team.shared.engine.data.stock.database.StockDataBase;
import com.team.shared.engine.data.stock.graph.StockGraphSeries;
import com.team.shared.engine.data.transaction.Transaction;
import com.team.shared.engine.data.user.User;
import com.team.shared.engine.data.user.holding.item.Item;
import com.team.shared.engine.data.xjc.generated.RseStock;
import com.team.shared.engine.message.Message;
import com.team.shared.engine.message.builder.out.BuildOutput_StockDataBase;
import com.team.shared.engine.timestamp.TimeStamp;
import com.team.ui.currency.Currency;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A Stock annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 *
 * @version 1.2
 */
@XmlAccessorType(XmlAccessType.FIELD) @XmlRootElement(name = "rse-stock")
public class Stock implements Serializable, Cloneable {

    public Stock clone() throws CloneNotSupportedException {
        Stock clone = (Stock) super.clone();
        clone.setDataBase(this.dataBase.clone());
        return clone;
    }

    private static final long serialVersionUID = -7818419776232107972L;

    @XmlElement(name = "rse-symbol", required = true) private String symbol;

    @XmlElement(name = "rse-company-name", required = true) private String
            companyName;

    /**
     * <i>price</i> is updated after each successful transaction, inside the
     * {@link Transaction#Transaction(Stock, String, long, long, User, User,
     * long)} {@code Constructor}.
     */
    @XmlElement(name = "rse-price", required = true) private long price;

    /**
     * A data-base of all the orders of the stock.
     * <p>Includes:</p>
     * <ul>
     *     <li>{@code awaitingBuyOrders}.</li>
     *     <li>{@code awaitingSellOrders}.</li>
     *     <li>{@code successfullyFinishedTransactions}.</li>
     * </ul>
     */
    @XmlElement(name = "rse-data-base") private StockDataBase dataBase =
            new StockDataBase();

    /**
     * Store here the {@link StockGraphSeries} data, required for printing a
     * <i>Stock-Graph</i>.
     */
    @XmlElement(name = "rse-stock-graph-series") private StockGraphSeries
            stockGraphSeries;

    public Stock(String symbol, String companyName, long price,
                 StockDataBase dataBase, StockGraphSeries stockGraph) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.dataBase = dataBase;
        this.stockGraphSeries = stockGraph;
    }

    /**
     * Must have a Default Constructor for {@code JAXBContext} <tt>.xml</tt>
     * load and save.
     */
    public Stock() {
        stockGraphSeries = new StockGraphSeries();
    }

    public Stock(RseStock rseStock) {
        this.symbol = rseStock.getRseSymbol();
        this.price = rseStock.getRsePrice();
        this.companyName = rseStock.getRseCompanyName();
        stockGraphSeries = new StockGraphSeries();
    }

    public StockGraphSeries getStockGraphSeries() {
        return stockGraphSeries;
    }

    public void setStockGraphSeries(StockGraphSeries stockGraphSeries) {
        this.stockGraphSeries = stockGraphSeries;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Stock stock = (Stock) o;
        return Objects.equals(symbol, stock.symbol) &&
                Objects.equals(companyName, stock.companyName) &&
                Objects.equals(price, stock.price) &&
                Objects.equals(dataBase, stock.dataBase);
    }

    @Override public int hashCode() {
        return Objects.hash(symbol, companyName, price, dataBase);
    }

    public StockDataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(StockDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override public String toString() {
        return "Stock{" + "symbol='" + symbol + '\'' + ", companyName='" +
                companyName + '\'' + ", price='" +
                Currency.decimalFormat.format(price) + '\'' +
                ", numOfTotalTransactions=" +
                dataBase.getSuccessfullyFinishedTransactions().getCollection()
                        .size() + ", [Total Transactions Period = " +
                Currency.decimalFormat.format(dataBase.getTotalPeriod(
                        dataBase.getSuccessfullyFinishedTransactions()
                                .getCollection())) + "]}";
    }

    /**
     * Reveal details of all the transactions in this {@code Stock}, sorted by
     * {@link TimeStamp}:
     * <p>Old is presented below, and New is presented above.</p>
     * <p>For each transaction show:</p>
     * <ul>
     *     <li>{@link TimeStamp}.</li>
     *     <li>{@code quantity} of sold stocks.</li>
     *     <li>{@code price} of selling.</li>
     *     <li>{@code transaction's period}.</li>
     * </ul>
     *
     * <blockquote>Note: if there were'nt any transactions with {@code this}
     * {@link Stock}, return a message.</blockquote>
     *
     * @param addTitleTabs   add here the amount of 'tab's to insert before the
     *                       Title.
     * @param addContentTabs add here the amount of 'tab's to insert before the
     *                       Content.
     * @return {@link String} of all the {@link Transaction}s of this {@code
     * Stock}.
     */
    public String getTransactionsToString(String addTitleTabs,
                                          String addContentTabs) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(addTitleTabs).append(" - ")
                .append("Transactions Made: ");

        stringBuilder.append("[Total Transactions Period = ")
                .append(Currency.decimalFormat.format(dataBase.getTotalPeriod(
                        dataBase.getSuccessfullyFinishedTransactions()
                                .getCollection()))).append("]:");
        stringBuilder.append("\n");

        if (dataBase.getSuccessfullyFinishedTransactions().getCollection()
                .size() == 0) {
            stringBuilder.append(addContentTabs)
                    .append(Message.Out.StockDataBase.printEmpty(
                            BuildOutput_StockDataBase.TypeOfCollection.SUCCESSFULLY_FINISHED_TRANSACTIONS));

        } else {
            stringBuilder.append(dataBase.getSuccessfullyFinishedTransactions()
                    .toString(addContentTabs));
        }
        return stringBuilder.toString();
    }

    /**
     * Reveal details of all the orders in this {@code Stock} sorted by {@link
     * TimeStamp}:
     * <p>Old is presented below, and New is presented above.</p>
     *
     * @param addTitleTabs   add here the amount of 'tab's to insert before the
     *                       Title.
     * @param addContentTabs add here the amount of 'tab's to insert before the
     *                       Content.
     * @return {@link String} of the presented {@link java.util.Collection}.
     */
    public String getAwaitingBuyOrdersToString(String addTitleTabs,
                                               String addContentTabs) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(addTitleTabs).append(" - ")
                .append("Awaiting 'Buy' Orders: ");

        stringBuilder.append("[Total 'Buy' Orders Period = ")
                .append(Currency.decimalFormat.format(dataBase.getTotalPeriod(
                        dataBase.getAwaitingBuyOrders().getCollection())))
                .append("]:");
        stringBuilder.append("\n");

        if (dataBase.getAwaitingBuyOrders().getCollection().size() == 0) {
            stringBuilder.append(addContentTabs)
                    .append(Message.Out.StockDataBase.printEmpty(
                            BuildOutput_StockDataBase.TypeOfCollection.AWAITING_BUY_ORDERS));

        } else {
            stringBuilder.append(dataBase.getAwaitingBuyOrders()
                    .toString(addContentTabs));
        }
        return stringBuilder.toString();
    }

    /**
     * Reveal details of all the orders in this {@code Stock} sorted by {@link
     * TimeStamp}:
     * <p>Old is presented below, and New is presented above.</p>
     *
     * @param addTitleTabs   add here the amount of 'tab's to insert before the
     *                       Title.
     * @param addContentTabs add here the amount of 'tab's to insert before the
     *                       Content.
     * @return {@link String} of the presented {@link java.util.Collection}.
     */
    public String getAwaitingSellOrdersToString(String addTitleTabs,
                                                String addContentTabs) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(addTitleTabs).append(" - ")
                .append("Awaiting 'Sell' Orders: ");

        stringBuilder.append("[Total 'Sell' Orders Period = ")
                .append(Currency.decimalFormat.format(dataBase.getTotalPeriod(
                        dataBase.getAwaitingSellOrders().getCollection())))
                .append("]:");
        stringBuilder.append("\n");

        if (dataBase.getAwaitingSellOrders().getCollection().size() == 0) {
            stringBuilder.append(addContentTabs)
                    .append(Message.Out.StockDataBase.printEmpty(
                            BuildOutput_StockDataBase.TypeOfCollection.AWAITING_SELL_ORDERS));

        } else {
            stringBuilder.append(dataBase.getAwaitingSellOrders()
                    .toString(addContentTabs));
        }
        return stringBuilder.toString();
    }

    /**
     * This method returns the {@code quantity} of {@code this} {@link Stock}
     * that is listed as a {@link Item} as a {@code Holding} of a given {@link
     * User}.
     *
     * @param user the user that {@code this} {@code Stock} serves as an {@code
     *             Item}.
     * @return the {@code quantity} of this {@code Stock} as the given {@code
     * User}'s {@code Item}.
     */
    public long getQuantity(User user) {
        List<Item> itemList = user.getHoldings().getCollection().stream()
                .filter(item -> item.getSymbol().equals(this.getSymbol()))
                .collect(Collectors.toList());

        return itemList.get(0).getQuantity();
    }

    public String getTransactionsPeriodCurrency() {
        return Currency.decimalFormat.format(dataBase.getTotalPeriod(
                dataBase.getSuccessfullyFinishedTransactions()
                        .getCollection()));
    }

    public String getPriceCurrency() {
        return Currency.decimalFormat.format(price);
    }

}
