package engine;

import application.dialog.FxDialogs;
import application.pane.resources.afterexecutionsummary.container.AfterExecutionOrderAndTransactionContainer;
import engine.collection.EngineCollection;
import load.Descriptor;
import message.Message;
import message.builder.err.BuildError;
import message.print.Log;
import message.print.MessagePrint;
import order.Order;
import order.OrderDirection;
import order.OrderType;
import stock.Stock;
import stock.Stocks;
import stock.database.StockDataBase;
import transaction.Transaction;
import user.User;
import user.Users;
import user.holding.Holdings;
import user.holding.item.Item;
import xjc.generated.RseStocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The Main Engine of the program.
 * <ul>
 * <li>Stores the data of the program.</li>
 * <li>Manages all commands given.</li>
 * </ul>
 *
 * @version 2.0
 */
public class Engine {

    /**
     * The program's stocks.
     */
    private static Stocks stocks;

    /**
     * The program's users.
     */
    private static Users users;

    /**
     * Store here the {@link Order}s and {@link Transaction}s after invoking an
     * <i>order-execution.</i>
     */
    private static AfterExecutionOrderAndTransactionContainer
            afterExecuteOrderAndTransactionContainer;

    /**
     * Empty constructor.
     * <blockquote><b>private constructor restricted to this {@code class}
     * itself</b></blockquote>
     */
    private Engine() {}

    /**
     * this method checks whether there is an ambiguity in <i>symbol(s)</i> and
     * <i>companyName(s)</i> in the stocks.
     *
     * @param collectionToCheck the collection to check.
     * @throws IOException with an appropriate message in case of an invalid
     *                     occurrence.
     */
    public static void checkValidStocks(
            EngineCollection<List<Stock>, Stock> collectionToCheck)
            throws IOException {

        // get the collection:
        List<Stock> list = collectionToCheck.getCollection();

        // bubble - compare:
        for (int i = 0; i < list.size(); ++i) {

            String i_symbol = list.get(i).getSymbol();
            String i_companyName = list.get(i).getCompanyName();
            for (int j = list.size() - 1; j > i; --j) {
                String j_symbol = list.get(j).getSymbol();
                String j_companyName = list.get(j).getCompanyName();
                if (i_symbol.equalsIgnoreCase(j_symbol)) {

                    /*
                     * found an equality of Strings between Symbols,
                     * means this File is invalid:
                     */
                    throw new IOException(Message.Err.XML.Load
                            .stocksInvalid_SymbolsAmbiguity() + "'" + i_symbol +
                            "' and '" + j_symbol + "'");
                }
                if (i_companyName.equalsIgnoreCase(j_companyName)) {

                    /*
                     * found an equality of Strings between companyNames,
                     * means this File is invalid:
                     */
                    throw new IOException(Message.Err.XML.Load
                            .stocksInvalid_CompanyNameAmbiguity() + "'" +
                            i_companyName + "' and '" + j_companyName + "'");
                }
            }
        }

        // passed all checks, thus valid.
    }

    /**
     * this method checks whether there is an ambiguity and validity in the
     * {@code users} {@code Collection}:
     * <ul>
     *     <li><i>checks for an ambiguity in the name(s)</i> of the {@code users}</li>
     *     <li>checks whether the <i>symbol(s)</i> of the stocks provided are
     *         truly stocks <i>symbol(s)</i> that already in the {@code Engine}.</li>
     * </ul>
     *
     * <p>Note: this must be invoked only <b>after</b> the
     * {@link #checkValidStocks(EngineCollection)} has been invoked on the given
     * {@link Stocks}, and determined them as valid.</p>
     *
     * <p>checking equality of the {@link Item}(s) {@code Symbol} with the
     * {@link Stock} {@code Symbol} with case-sensitive.</p>
     *
     * @param collectionToCheck the collection to check.
     * @param stocks            the {@link Stocks} to <i>compare</i> each {@link
     *                          User}s {@link Holdings} validity with.
     * @throws IOException with an appropriate message in case of an invalid
     *                     occurrence.
     */
    public static void checkValidUsers(
            EngineCollection<List<User>, User> collectionToCheck, Stocks stocks)
            throws IOException {

        // get the collection:
        List<User> usersCollection = collectionToCheck.getCollection();

        // bubble - compare:
        for (int i = 0; i < usersCollection.size(); ++i) {
            String i_name = usersCollection.get(i).getName();
            for (int j = usersCollection.size() - 1; j > i; --j) {
                String j_name = usersCollection.get(j).getName();
                if (i_name.equalsIgnoreCase(j_name)) {

                    /*
                     * found an equality of Strings between Names of users,
                     * means this File is invalid:
                     */
                    throw new IOException(new BuildError().getMessage() +
                            "Invalid Stocks - There is an ambiguity in the 'name' [String]s of the users: " +
                            "'" + i_name + "' and '" + j_name + "'");
                }
            }

            // check the user's holdings:
            checkValidHoldings(usersCollection.get(i), stocks);
        }

        // passed all checks, thus valid.
    }

    /**
     * This method checks if a given {@link User}'s {@link Holdings} are valid.
     *
     * @param userToCheck the {@link User} to check its {@link Holdings}
     *                    validity.
     * @param stocks      the {@link Stocks} to <i>compare</i> the {@link
     *                    User}'s {@link Holdings} validity with.
     * @throws IOException if there is the {@link User} has been found as
     *                     invalid.
     */
    private static void checkValidHoldings(User userToCheck, Stocks stocks)
            throws IOException {
        List<Stock> stockCollection = stocks.getCollection();
        Holdings userHoldings = userToCheck.getHoldings();
        if (userHoldings != null) {
            List<Item> itemCollection = userHoldings.getCollection();

            /*
             * bubble - compare:
             * check if the Symbol of each Item is not equal to the
             * Symbols of the Stocks that are already in the Engine:
             */
            for (Item item : itemCollection) {
                String currentItemSymbol = item.getSymbol();

                for (int n = 0; n < stockCollection.size(); ++n) {

                    // check for equality of Strings with case-sensitive:
                    if (currentItemSymbol
                            .equals(stockCollection.get(n).getSymbol())) {

                        /*
                         * if there is an equality of Strings with
                         * case-sensitive thus this is a valid item.
                         * so there is no need to continue check.
                         */
                        break;
                    } else if ((n == (stockCollection.size() - 1)) &&
                            (!(currentItemSymbol.equals(stockCollection.get(n)
                                    .getSymbol())))) {

                        /*
                         * this is the last Stock available, and there
                         * is no equality of Strings between the Symbols,
                         * means this File is invalid:
                         */
                        throw new IOException(new BuildError().getMessage() +
                                "found a problem with the user: " + "'" +
                                userToCheck.getName() + "': " +
                                "the 'symbol' of the item: " + "'" +
                                currentItemSymbol + "'" +
                                " was not found as a Stock 'symbol' in the system");
                    }

                }

            }

            // else, all symbols in the 'holdings' collection are valid.
        }
    }

    /**
     * @param symbol the key to find the requested {@link Stock}: case
     *               in-sensitive.
     * @return the {@link Stock} that has the {@code Symbol} that was provided.
     * @throws IOException <ul>
     *                     <li>if we didn't find the {@link Stock} that has
     *                     the given {@code Symbol}.</li>
     *                     <li>if there are no {@link #stocks} at all.</li>
     *                     </ul>
     */
    public static Stock getStockBySymbol(String symbol) throws IOException {

        try {

            /*
             * get the collection:
             * (may throw NullPointerException if there are no Stocks already)
             */
            List<Stock> list = stocks.getCollection();

            // search for the given Symbol:
            for (Stock i : list) {
                String i_symbol = i.getSymbol();
                if (i_symbol.equalsIgnoreCase(symbol)) {

                    /*
                     * found an equality of Strings between Symbols,
                     * means we found the desired stock successfully.
                     */
                    return i;
                }
            }
        } catch (NullPointerException e) {
            throw new IOException(Message.Err.Stocks.getEmpty());
        }
        throw new IOException(Message.Err.Stocks.unFoundSymbol(symbol));
    }

    /**
     * @return wrap to a {@link Descriptor} that contains all the fields of
     * {@code this} class, such as:
     * <ul>
     *     <li>{@link #stocks} field.</li>
     *     <li>{@link #users} field.</li>
     * </ul>
     */
    public static Descriptor createDescriptor() {
        Descriptor descriptor = new Descriptor();
        descriptor.setStocks(stocks);
        descriptor.setUsers(users);
        return descriptor;
    }

    /**
     * This method checks if there are valid stocks loaded in the system.
     *
     * @return boolean: <ul>
     * <li>true if the stocks are valid.</li>
     * <li>else false.</li>
     * </ul>
     */
    public static boolean isStocks() {

        // first of all check if there are Stocks available in the system:
        try {
            Engine.getStocks();
            return true;
        } catch (IOException e) {
            MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
            return false;
        }
    }

    public static boolean isUsers() {

        // first of all check if there are Users available in the system:
        try {
            Engine.getUsers();
            return true;
        } catch (IOException e) {
            // MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
            // TODO: message disabled
            return false;
        }
    }

    /**
     * @return {@link #stocks} of the program.
     * @throws IOException if the {@link #stocks} are {@code null} - means
     *                     uninitialized.
     */
    public static Stocks getStocks() throws IOException {
        if (stocks != null) {
            return stocks;
        } else {
            throw new IOException(Message.Err.Stocks.getEmpty());
        }
    }

    public static void setStocks(Stocks stocks) {
        Engine.stocks = stocks;
    }

    public static void setStocks(RseStocks stocks) {
        Engine.stocks = new Stocks(stocks);
    }

    /**
     * @return {@link #users} of the program.
     * @throws IOException if the {@link #users} are {@code null} - means
     *                     uninitialized.
     */
    public static Users getUsers() throws IOException {
        if (users != null) {
            return users;
        } else {
            throw new IOException(Message.Err.Users.getEmpty());
        }
    }

    public static void setUsers(Users users) {
        Engine.users = users;
    }

    public static AfterExecutionOrderAndTransactionContainer getAfterExecuteOrderAndTransactionContainer() {
        return afterExecuteOrderAndTransactionContainer;
    }

    public static void setAfterExecuteOrderAndTransactionContainer(
            AfterExecutionOrderAndTransactionContainer afterExecuteOrderAndTransactionContainer) {
        Engine.afterExecuteOrderAndTransactionContainer =
                afterExecuteOrderAndTransactionContainer;
    }

    /**
     * <b>The {@code Engine}'s core method.</b>
     * <p>
     * This method checks a single {@link Stock} (by passing its {@code Symbol}
     * as a parameter), reads all its {@link Order}(s) lists, and calculates
     * whether it is possible to create a {@link Transaction} between two {@link
     * Order}(s).
     * </p>
     *
     * @param afterExecuteOrderAndTransactionContainer the {@code Container}
     *                                                 that <i>saves</i> the
     *                                                 {@link Order}s and {@link
     *                                                 Transaction}s
     *                                                 <i>made</i> after an
     *                                                 <i>order-execution.</i>
     * @param stock                                    the stock the user wishes
     *                                                 to check.
     * @param arrivedOrder                             place here the <i>last
     *                                                 placed</i> {@link Order}
     *                                                 of in the stock's
     *                                                 data-base. this means,
     *                                                 that on the calculation
     *                                                 process of interaction
     *                                                 between two opposite
     *                                                 already placed orders,
     *                                                 this <i>last placed</i>
     *                                                 order would match the
     *                                                 desiredLimitPrice placed
     *                                                 in another <i>opposite
     *                                                 already placed</i> order.
     *                                                 thus means, the {@link
     *                                                 Transaction}'s desiredLimitPrice
     *                                                 would be determined by
     *                                                 the
     *                                                 <i>opposite already
     *                                                 placed</i> order desiredLimitPrice.
     * @see #checkForOppositeAlreadyPlacedOrders
     * @see #makeATransaction
     * @see #checkRemainders
     * @see #checkOppositeAlreadyPlacedOrderRemainder
     * @see #checkArrivedOrderRemainder
     */
    public static void calcOrdersOfASingleStock(
            AfterExecutionOrderAndTransactionContainer afterExecuteOrderAndTransactionContainer,
            Stock stock, Order arrivedOrder) {
        setAfterExecuteOrderAndTransactionContainer(
                afterExecuteOrderAndTransactionContainer);

        // get the dataBase of this Stock:
        StockDataBase dataBase = stock.getDataBase();

        /*
         * get the 'Buy' Orders Collection, and the 'Sell' Orders Collection,
         * sorted by desiredLimitPrice/timeStamp priority:
         *
         * Orders are sorted with the highest desiredLimitPrice at the top (= first),
         * and the lowest desiredLimitPrice at the bottom (= last).
         * upon finding that prices are equal, they are sorted by timeStamp priority.
         */
        List<Order> buyOrders = dataBase.getAwaitingBuyOrders().getCollection();
        List<Order> sellOrders =
                dataBase.getAwaitingSellOrders().getCollection();

        /*
         * Update here whether the 'arrivedOrder' was treated in part or in
         * its entirety.
         */
        AtomicBoolean arrivedOrderWasTreated = new AtomicBoolean(false);


        /*
         * Store here the serial time of the 'added' Order/Transaction.
         *
         * Important: must initialize with "1", so that it will be indicated
         * that if an Order has a serialTime value of "0" it would mean that the
         * Order is NOT a 'remained order'.
         */
        AtomicLong serialTime = new AtomicLong(1);

        // if the arrived Order is a 'Buy' Order:
        if (arrivedOrder.getOrderDirection() == OrderDirection.BUY) {
            checkForOppositeAlreadyPlacedOrders(stock, sellOrders, arrivedOrder,
                    arrivedOrderWasTreated, serialTime);

            // if the arrived Order is a 'Sell' Order:
        } else if (arrivedOrder.getOrderDirection() == OrderDirection.SELL) {
            checkForOppositeAlreadyPlacedOrders(stock, buyOrders, arrivedOrder,
                    arrivedOrderWasTreated, serialTime);
        }

        /*
         * Check if the order has not been fulfilled in its entirety nor
         * partially yet:
         */
        if (!arrivedOrderWasTreated.get()) {

            // Add a newLine to the Log-View:
            Log.getMessageLog().append("\n");

            MessagePrint.println(MessagePrint.Stream.OUT,
                    "Note: The order has not been fulfilled in its " +
                            "entirety nor partially yet.");
        }
    }

    private static void checkForOppositeAlreadyPlacedOrders(Stock stock,
                                                            List<Order> oppositeAlreadyPlacedOrders,
                                                            Order arrivedOrder,
                                                            AtomicBoolean arrivedOrderWasTreated,
                                                            AtomicLong serialTime) {

        /*
         * search the 'opposite already placed' Orders of this Stock
         * (by descending desiredLimitPrice/timeStamp):
         */
        for (Iterator<Order> it = oppositeAlreadyPlacedOrders.iterator();
             it.hasNext(); ) {
            Order oppositeAlreadyPlacedOrder = it.next();

            /*
             * check if the 'arrivedOrder' is a 'Sell' Order.
             * compare orders: if 'buy' >= 'sell':
             */
            if (checkForOppositeBuyAlreadyPlacedOrders(stock, arrivedOrder, it,
                    oppositeAlreadyPlacedOrder, arrivedOrderWasTreated,
                    serialTime)) {}

            /*
             * check if the 'arrivedOrder' is a 'Buy' Order.
             * compare orders: if 'buy' >= 'sell':
             */
            else if (checkForOppositeSellAlreadyPlacedOrders(stock,
                    arrivedOrder, it, oppositeAlreadyPlacedOrder,
                    arrivedOrderWasTreated, serialTime)) {}

            /*
             * we found that there are no matching 'opposite already placed' Orders,
             * so we do not make a Transaction,
             * and the 'arrived' Order stays as it was in the data-base.
             */
        }
    }

    private static boolean checkForOppositeBuyAlreadyPlacedOrders(Stock stock,
                                                                  Order arrivedOrder,
                                                                  Iterator<Order> it,
                                                                  Order oppositeAlreadyPlacedOrder,
                                                                  AtomicBoolean arrivedOrderWasTreated,
                                                                  AtomicLong serialTime) {

        /*
         * check if the 'arrivedOrder' is a 'Sell' Order.
         * compare orders: if 'buy' >= 'sell':
         */
        if ((oppositeAlreadyPlacedOrder.getOrderDirection() ==
                OrderDirection.BUY) &&
                (oppositeAlreadyPlacedOrder.getDesiredLimitPrice() >=
                        arrivedOrder.getDesiredLimitPrice())) {

            // only if the 'arrivedOrder' wasn't removed from the data-base yet:
            checkForOppositeAlreadyPlacedOrders_DependencyOnDirection(stock,
                    arrivedOrder, it, oppositeAlreadyPlacedOrder,
                    stock.getDataBase().getAwaitingSellOrders().getCollection(),
                    arrivedOrderWasTreated, serialTime);
            return true;
        } else {return false;}
    }

    private static boolean checkForOppositeSellAlreadyPlacedOrders(Stock stock,
                                                                   Order arrivedOrder,
                                                                   Iterator<Order> it,
                                                                   Order oppositeAlreadyPlacedOrder,
                                                                   AtomicBoolean arrivedOrderWasTreated,
                                                                   AtomicLong serialTime) {

        /*
         * check if the 'arrivedOrder' is a 'Buy' Order.
         * compare orders: if 'buy' >= 'sell':
         */
        if ((oppositeAlreadyPlacedOrder.getOrderDirection() ==
                OrderDirection.SELL) &&
                (oppositeAlreadyPlacedOrder.getDesiredLimitPrice() <=
                        arrivedOrder.getDesiredLimitPrice())) {

            // only if the 'arrivedOrder' wasn't removed from the data-base yet:
            checkForOppositeAlreadyPlacedOrders_DependencyOnDirection(stock,
                    arrivedOrder, it, oppositeAlreadyPlacedOrder,
                    stock.getDataBase().getAwaitingBuyOrders().getCollection(),
                    arrivedOrderWasTreated, serialTime);
            return true;
        } else { return false; }
    }

    private static void checkForOppositeAlreadyPlacedOrders_DependencyOnDirection(
            Stock stock, Order arrivedOrder, Iterator<Order> it,
            Order oppositeAlreadyPlacedOrder, List<Order> OrderList,
            AtomicBoolean arrivedOrderWasTreated, AtomicLong serialTime) {

        // only if the 'arrivedOrder' wasn't removed from the data-base yet:
        if (OrderList.contains(arrivedOrder)) {
            makeTransactionAndCheckRemainders(stock, it, arrivedOrder,
                    oppositeAlreadyPlacedOrder, serialTime);
            arrivedOrderWasTreated.set(true);
        }
    }

    private static void makeTransactionAndCheckRemainders(Stock stock,
                                                          Iterator<Order> it,
                                                          Order arrivedOrder,
                                                          Order oppositeAlreadyPlacedOrder,
                                                          AtomicLong serialTime) {

        Transaction transaction = makeATransaction(stock, arrivedOrder,
                oppositeAlreadyPlacedOrder, serialTime);

        // check if there are remainders:
        checkRemainders(stock, it, arrivedOrder, oppositeAlreadyPlacedOrder,
                transaction, serialTime);
    }

    private static Transaction makeATransaction(Stock stock, Order arrivedOrder,
                                                Order oppositeAlreadyPlacedOrder,
                                                AtomicLong serialTime) {

        /*
         * make a Transaction:
         * its timeStamp is the arrivedOrder's timeStamp.
         * its quantity is the minimum Quantity between the two Orders.
         * its desiredLimitPrice is the 'opposite already placed' Order.
         */

        // calculate the Transaction's Quantity:
        long quantityOfTransaction = Math.min(arrivedOrder.getQuantity(),
                oppositeAlreadyPlacedOrder.getQuantity());

        Transaction transaction = null;

        // create Transaction:
        if (arrivedOrder.getOrderDirection() == OrderDirection.BUY) {
            transaction = new Transaction(stock, arrivedOrder.getTimeStamp(),
                    quantityOfTransaction,
                    oppositeAlreadyPlacedOrder.getDesiredLimitPrice(),
                    arrivedOrder.getRequestingUser(),
                    oppositeAlreadyPlacedOrder.getRequestingUser(),
                    serialTime.get());
            serialTime.set(serialTime.get() + 1);
        } else if (arrivedOrder.getOrderDirection() == OrderDirection.SELL) {
            transaction = new Transaction(stock, arrivedOrder.getTimeStamp(),
                    quantityOfTransaction,
                    oppositeAlreadyPlacedOrder.getDesiredLimitPrice(),
                    oppositeAlreadyPlacedOrder.getRequestingUser(),
                    arrivedOrder.getRequestingUser(), serialTime.get());
            serialTime.set(serialTime.get() + 1);
        }

        // add Transaction:
        stock.getDataBase().getSuccessfullyFinishedTransactions()
                .getCollection().addFirst(transaction);
        MessagePrint.println(MessagePrint.Stream.OUT,
                Message.Out.StockDataBase.newSuccessAdd(transaction));
        afterExecuteOrderAndTransactionContainer.getTransactions()
                .addFirst(transaction);

        return transaction;
    }

    private static void checkRemainders(Stock stock, Iterator<Order> it,
                                        Order arrivedOrder,
                                        Order oppositeAlreadyPlacedOrder,
                                        Transaction transaction,
                                        AtomicLong serialTime) {

        // check if there is a remainder in the 'opposite already placed' Order:
        checkOppositeAlreadyPlacedOrderRemainder(it, oppositeAlreadyPlacedOrder,
                transaction);

        // check if there is a remainder in the arrivedOrder:
        checkArrivedOrderRemainder(stock, arrivedOrder, transaction,
                serialTime);
    }

    private static void checkOppositeAlreadyPlacedOrderRemainder(
            Iterator<Order> it, Order oppositeAlreadyPlacedOrder,
            Transaction transaction) {

        // check if there is a remainder in the 'opposite already placed' Order:
        long alreadyRemainderQuantity =
                oppositeAlreadyPlacedOrder.getQuantity() -
                        transaction.getQuantity();
        if (alreadyRemainderQuantity > 0) {

            /*
             * there is a remainder in the oppositeAlreadyPlacedOrder,
             * set the Quantity of it to the updated 'alreadyRemainderQuantity':
             */
            oppositeAlreadyPlacedOrder.setQuantity(alreadyRemainderQuantity);
        } else {

            /*
             * if the 'opposite already placed' Order's quantity remainder is no more than 0,
             * remove the 'opposite already placed' Order from data-base:
             */
            it.remove();
        }
    }

    private static void checkArrivedOrderRemainder(Stock stock,
                                                   Order arrivedOrder,
                                                   Transaction transaction,
                                                   AtomicLong serialTime) {

        // check if there is a remainder in the arrivedOrder:
        long arrivedRemainderQuantity =
                arrivedOrder.getQuantity() - transaction.getQuantity();
        if (arrivedRemainderQuantity > 0) {

            /*
             * there is a remainder in the 'arrivedOrder',
             * set the Quantity of the 'arrivedOrder'
             * to the updated 'arrivedRemainderQuantity':
             */
            arrivedOrder.setQuantity(arrivedRemainderQuantity);

            /*
             * if the 'arrivedOrder' Type is 'MKT', set the remainder of
             * this arrivedOrder's 'desiredLimitPrice' to be calculated again:
             */
            if (arrivedOrder.getOrderType() == OrderType.MKT) {
                arrivedOrder.setDesiredLimitPrice(
                        calcDesiredLimitPriceOfMKTOrder(stock,
                                arrivedOrder.getOrderDirection()));
            }

            // Create remained order with a correct 'serialTime':
            Order remainedOrder = new Order(arrivedOrder);
            remainedOrder.setSerialTimeOfRemainedOrder(serialTime.get());
            serialTime.set(serialTime.get() + 1);

            MessagePrint.println(MessagePrint.Stream.OUT,
                    "The Order has a remainder:\n\t" + remainedOrder);

            // Add 'remainedOrder' to 'afterExecuteOrderAndTransactionContainer':
            afterExecuteOrderAndTransactionContainer.getRemainderOrders()
                    .addFirst(remainedOrder);
        } else {

            /*
             * if the 'arrived' Order's quantity remainder is no more than 0,
             * remove the 'arrived' Order from data-base:
             */
            checkArrivedOrderRemainder_RemoveArrivedOrder(stock.getDataBase(),
                    arrivedOrder);
        }
    }

    private static void checkArrivedOrderRemainder_RemoveArrivedOrder(
            StockDataBase dataBase, Order arrivedOrder) {

        /*
         * if the 'arrived' Order's quantity remainder is no more than 0,
         * remove the 'arrived' Order from data-base:
         */
        if (arrivedOrder.getOrderDirection() == OrderDirection.BUY) {
            if (dataBase.getAwaitingBuyOrders().getCollection()
                    .remove(arrivedOrder)) {
                MessagePrint.println(MessagePrint.Stream.OUT,
                        Message.Out.StockDataBase
                                .printOrderPerformedInItsEntirety());
                // FxDialogs.showInformation("INFO", Message.Out.StockDataBase
                //         .printOrderPerformedInItsEntirety());
            } else {
                MessagePrint.println(MessagePrint.Stream.ERR,
                        new BuildError().getMessage() +
                                Message.Err.Order.removeFail());
                FxDialogs.showError("ERROR", new BuildError().getMessage() +
                        Message.Err.Order.removeFail());
            }
        } else if (arrivedOrder.getOrderDirection() == OrderDirection.SELL) {

            if (dataBase.getAwaitingSellOrders().getCollection()
                    .remove(arrivedOrder)) {
                MessagePrint.println(MessagePrint.Stream.OUT,
                        Message.Out.StockDataBase
                                .printOrderPerformedInItsEntirety());
                // FxDialogs.showInformation("INFO", Message.Out.StockDataBase
                //         .printOrderPerformedInItsEntirety());
            } else {
                MessagePrint.println(MessagePrint.Stream.ERR,
                        new BuildError().getMessage() +
                                Message.Err.Order.removeFail());
                FxDialogs.showError("ERROR", new BuildError().getMessage() +
                        Message.Err.Order.removeFail());
            }
        }
    }

    /**
     * <p>This method is calculating the <i>{@code desiredLimitPrice}</i> for
     * the current {@code MKT} {@link Order}, based on its {@link
     * OrderDirection}.</p>
     *
     * <p>The method searches if there are <i>opposite {@code Direction}
     * already placed</i> {@link Order}s in the given {@link Stock}'s {@code
     * data-base}.</p>
     *
     * <p>If there are such <i>opposite already placed</i> {@link Order}s, the
     * method calculates the
     * <i>{@code desiredLimitPrice}</i> of the {@code MKT} {@link Order} to be
     * as the <tt>first</tt> <i>opposite already placed</i> {@link Order}'s
     * <i>{@code desiredLimitPrice}</i>.</p>
     *
     * <p>If there are <tt>no</tt> such <i>opposite already placed</i> {@link
     * Order}s, the method calculates the
     * <i>{@code desiredLimitPrice}</i> of the {@code MKT} {@link Order} to be
     * as the <i>current</i> <i>{@code price}</i> of the given {@link
     * Stock}.</p>
     *
     * @param stock          the current {@link Stock} to deal with.
     * @param orderDirection the current {@code MKT} {@link Order}'s {@code
     *                       Direction}.
     * @return the calculated <i>{@code desiredLimitPrice}</i> of the current
     * {@code MKT} {@link Order}.
     */
    public static long calcDesiredLimitPriceOfMKTOrder(Stock stock,
                                                       OrderDirection orderDirection) {
        long desiredLimitPrice;
        if ((orderDirection == OrderDirection.BUY) &&
                (stock.getDataBase().getAwaitingSellOrders().getCollection()
                        .size() > 0)) {
            desiredLimitPrice =
                    stock.getDataBase().getAwaitingSellOrders().getCollection()
                            .getFirst().getDesiredLimitPrice();
        } else if ((orderDirection == OrderDirection.SELL) &&
                (stock.getDataBase().getAwaitingBuyOrders().getCollection()
                        .size() > 0)) {
            desiredLimitPrice =
                    stock.getDataBase().getAwaitingBuyOrders().getCollection()
                            .getFirst().getDesiredLimitPrice();
        } else {
            desiredLimitPrice = stock.getPrice();
        }

        return desiredLimitPrice;
    }

    /**
     * Inserts a {@link User} to the {@link #users} field. In case the {@link
     * #users} field is {@code null}, then initialize it and insert the given
     * {@link User}.
     *
     * @param user the {@link User} to be inserted to the {@link Users} field.
     */
    public static void insertUserForced(User user) {
        try {
            Engine.getUsers().getCollection().add(user);
        } catch (java.io.IOException e) {
            Users users = new Users();
            users.setCollection(new ArrayList<>());
            Engine.setUsers(users);
            insertUserForced(user);
        }
    }

    /**
     * Finds a {@link User} by {@link User#getName()} in the {@link #users}
     * field. In case the {@link #users} field is {@code null}, then return
     * null.
     *
     * @param user the {@link User} to be searched in the {@link Users} field.
     */
    public static User findUserByNameForced(User user) {
        User returnValue = null;
        String userName = user.getName();
        try {
            for (User currentUser : Engine.getUsers().getCollection()) {
                if (currentUser.getName().equalsIgnoreCase(userName)) {
                    returnValue = currentUser;
                    break;
                }
            }
        } catch (java.io.IOException e) {
            return returnValue;
        }
        return returnValue;
    }

    // TODO: may remove this:
    //
    // /**
    //  * Gets the {@link Users} {@link List} {@link java.util.Collection} by using
    //  * the {@link #getUsers()} In case the {@link #users} field is {@code null},
    //  * initialize the field with {@code new Users()} and return it.
    //  */
    // public static List<User> getUsersListForced() {
    //     try {
    //         System.out.println(getUsers().getCollection());
    //         return getUsers().getCollection();
    //     } catch (java.io.IOException e) {
    //         setUsers(new Users(true));
    //         return getUsersListForced();
    //     }
    // }


}
