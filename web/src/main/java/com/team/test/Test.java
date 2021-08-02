package com.team.test;

import com.team.shared.dto.UserDTO;
import com.team.shared.engine.data.order.Order;
import com.team.shared.engine.data.order.OrderDirection;
import com.team.shared.engine.data.order.OrderType;
import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.engine.Engine;
import com.team.web.service.ExecuteService;
import com.team.web.service.JaxbService;
import com.team.web.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Used for <b>programmers</b> only, for <t>testing</t> purposes.
 */
public class Test {

    @Autowired UserService userService;
    @Autowired JaxbService jaxbService;
    @Autowired ExecuteService executeService;

    private void createUsers() {
        userService.createUser(new UserDTO("tal", "USER"));
        userService.createUser(new UserDTO("dan", "USER"));
        userService.createUser(new UserDTO("admin", "ADMIN"));

        userService.insertToSignedInUsersList("tal");
        userService.insertToSignedInUsersList("dan");
        userService.insertToSignedInUsersList("admin");
    }

    private void unmarshalTal() {
        jaxbService.unmarshal(Engine.findUserByNameForced("tal"),
                "XMLresources\\heaver-user.xml");
    }

    @SneakyThrows private void executeIOC() {
        Stock stock = Engine.getStockBySymbol("GOOGL");
        executeService.executeOrder(stock,
                new Order(OrderDirection.BUY, OrderType.LMT, 5, 110, "tal"));
        executeService.executeOrder(stock,
                new Order(OrderDirection.BUY, OrderType.LMT, 10, 100, "tal"));
        executeService.executeOrder(stock,
                new Order(OrderDirection.SELL, OrderType.LMT, 10, 100, "tal"));
        executeService.executeOrder(stock,
                new Order(OrderDirection.SELL, OrderType.LMT, 10, 110, "tal"));
    }

    public void testIOC() {
        createUsers();
        unmarshalTal();
        executeIOC();
    }
}
