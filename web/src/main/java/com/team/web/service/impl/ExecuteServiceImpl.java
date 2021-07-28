package com.team.web.service.impl;

import com.team.shared.engine.data.execute.AfterExecutionOrderAndTransactionDTO;
import com.team.shared.engine.data.order.Order;
import com.team.shared.engine.data.order.OrderDirection;
import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.engine.Engine;
import com.team.web.service.ExecuteService;
import org.springframework.stereotype.Service;

/**
 * A {@code Service} which serves for handling {@link Order} <i>executions</i>
 * performances to the <i>database</i> (Which on this project, stored in {@link
 * Engine#getAfterExecutionOrderAndTransactionDTO()}).
 * <p>
 * This service is ready for future use of <i>database</i> implementations.
 * </p>
 */
@Service public class ExecuteServiceImpl implements ExecuteService {

    /**
     * Warning: this implementation <b>does not</b> check that {@link
     * Engine#getStocks()} {@code != null}.
     *
     * @param stock the {@link Stock} to execute the {@link Order} to.
     * @param order the {@link Order} to pend execution.
     */
    @Override public void executeOrder(Stock stock, Order order) {
        insertOrder(stock, order);

        // Calc this newly placed order with the matching already placed Orders:
        Engine.calcOrdersOfASingleStock(
                new AfterExecutionOrderAndTransactionDTO(), stock, order);
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
}
