package com.team.web.service.impl;

import com.team.shared.engine.data.order.Order;
import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.engine.Engine;
import com.team.web.service.ExecuteService;
import org.springframework.stereotype.Service;

/**
 * A {@code Service} which serves for handling {@link Order} <i>executions</i>
 * performances to the <i>database</i> (Which on this project, stored in {@link
 * Engine#getAfterExecuteOrderAndTransactionDTO()}).
 * <p>
 * This service is ready for future use of <i>database</i> implementations.
 * </p>
 */
@Service public class ExecuteServiceImpl implements ExecuteService {

    @Override public void executeOrder(Stock stock, Order order) {

    }
}
