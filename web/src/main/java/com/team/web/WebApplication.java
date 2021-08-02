package com.team.web;

import com.team.shared.dto.UserDTO;
import com.team.shared.engine.data.order.Order;
import com.team.shared.engine.data.order.OrderDirection;
import com.team.shared.engine.data.order.OrderType;
import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.engine.Engine;
import com.team.web.service.ExecuteService;
import com.team.web.service.JaxbService;
import com.team.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication public class WebApplication
        implements CommandLineRunner {

    @Autowired UserService userService;

    @Autowired JaxbService jaxbService;

    @Autowired ExecuteService executeService;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override public void run(String... args) throws Exception {

        // DEBUG : for tests:
        userService.createUser(new UserDTO("tal", "USER"));
        userService.createUser(new UserDTO("dan", "USER"));
        userService.createUser(new UserDTO("admin", "ADMIN"));
        jaxbService.unmarshal(Engine.findUserByNameForced("tal"),
                "C:\\Tal\\Code\\java\\rolling_ex_3\\XMLresources\\heaver-user.xml");
        Stock stock = Engine.getStockBySymbol("GOOGL");
        executeService.executeOrder(stock,new Order(OrderDirection.BUY,
                OrderType.LMT,5,110,"tal"));
        executeService.executeOrder(stock,new Order(OrderDirection.BUY,
                OrderType.LMT,10,100,"tal"));
        executeService.executeOrder(stock,new Order(OrderDirection.SELL,
                OrderType.LMT,10,100,"tal"));
        executeService.executeOrder(stock,new Order(OrderDirection.SELL,
                OrderType.LMT,10,110,"tal"));
    }
}
