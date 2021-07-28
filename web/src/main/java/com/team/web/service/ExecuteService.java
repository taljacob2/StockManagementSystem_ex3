package com.team.web.service;

import com.team.shared.engine.data.order.Order;
import com.team.shared.engine.data.stock.Stock;
import com.team.shared.model.notification.Notification;

import java.util.Optional;

/**
 * An <i>interface</i> that contains other methods for the {@code Service} of
 * handling {@link Order} <i>executions</i> transfers through <i>database</i>.
 *
 * @see com.team.web.service.impl.ExecuteServiceImpl
 */
public interface ExecuteService {

    /**
     * This method executes an {@link Order} to a given {@link Stock}.
     *
     * @param stock the {@link Stock} to execute the {@link Order} to.
     * @param order the {@link Order} to pend execution.
     */
    Optional<Notification> executeOrder(Stock stock, Order order);

}
