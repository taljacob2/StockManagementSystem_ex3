package com.team.web.service.impl;

import com.rits.cloning.Cloner;
import com.team.shared.engine.data.execute.AfterExecutionOrderAndTransactionDTO;
import com.team.shared.engine.data.order.Order;
import com.team.shared.engine.data.order.OrderDirection;
import com.team.shared.engine.data.order.OrderType;
import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.data.user.notification.Notifications;
import com.team.shared.engine.engine.Engine;
import com.team.shared.engine.engine.unmarshal.EngineInstance;
import com.team.shared.model.notification.Notification;
import com.team.web.service.ExecuteService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * A {@code Service} which serves for handling {@link Order} <i>executions</i>
 * performances to the <i>database</i> (Which on this project, stored in {@link
 * Engine#getAfterExecutionOrderAndTransactionDTO()}).
 * <p>
 * This service is ready for future use of <i>database</i> implementations.
 * </p>
 */
@Slf4j @Service public class ExecuteServiceImpl implements ExecuteService {

    /**
     * Warning: this implementation <b>does not</b> check that {@link
     * Engine#getStocks()} {@code != null}.
     *
     * @param stock the {@link Stock} to execute the {@link Order} to.
     * @param order the {@link Order} to pend execution.
     * @return if there is a {@link Notification}.
     */
    @SneakyThrows @Override public void executeOrder(Stock stock, Order order) {

        // Validate orderType:
        validateType(stock, order);

        // Clone:
        EngineInstance engineTry = new EngineInstance(true);
        Cloner cloner = new Cloner();
        cloner.dontClone(Notifications.class);
        EngineInstance engineBackup = cloner.deepClone(engineTry);

        // Insert Order:
        insertOrder(stock, order);

        // Calc this newly placed order with the matching already placed Orders:
        if (Engine.calcOrdersOfASingleStock(
                new AfterExecutionOrderAndTransactionDTO(), stock, order)) {

            // Restore backup needed:
            engineBackup.transferToEngine();
        }
    }

    /**
     * Add the {@link Order} to the according 'awaiting orders' {@link
     * java.util.Collection} in the {@link Stock#getDataBase()}.
     *
     * @param stock the {@link Stock} to execute the {@link Order} to.
     * @param order the {@link Order} to pend execution.
     */
    private void insertOrder(Stock stock, Order order) {
        if (order.getOrderDirection() == OrderDirection.BUY) {
            stock.getDataBase().getAwaitingBuyOrders().getCollection()
                    .sortedAdd(order);
        } else if (order.getOrderDirection() == OrderDirection.SELL) {
            stock.getDataBase().getAwaitingSellOrders().getCollection()
                    .sortedAdd(order);
        }
    }

    /**
     * Validates the {@link OrderType} of the {@link Order}, and <i>edits</i> it
     * accordingly.
     *
     * @param stock the {@link Stock} to execute the {@link Order} to.
     * @param order the {@link Order} to pend execution.
     */
    private void validateType(Stock stock, Order order) {
        if (order.getOrderType() == OrderType.MKT) {

            // Set the 'desiredLimitPrice':
            order.setDesiredLimitPrice(
                    Engine.calcDesiredLimitPriceOfMKTOrder(stock,
                            order.getOrderDirection()));
        }
    }
}
