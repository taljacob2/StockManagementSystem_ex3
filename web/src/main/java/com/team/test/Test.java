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
import org.springframework.stereotype.Component;

/**
 * Used for <b>programmers</b> only, for <t>testing</t> purposes.
 *<p>
 * Use in:
 * <code>
 *
 *     `@SpringBootApplication` public class WebApplication
 *         implements CommandLineRunner {
 *
 *     public static void main(String[] args) {
 *         SpringApplication.run(WebApplication.class, args);
 *     }
 *
 *     `@Override` public void run(String... args) throws Exception {
 *         Test test = new Test();
 *         test.testFOK();
 *     }
 *
 * }
 *
 * </code>
 * </p>
 */
@Component public class Test {

    private final UserService userService;
    private final JaxbService jaxbService;
    private final ExecuteService executeService;

    public Test(UserService userService, JaxbService jaxbService,
                ExecuteService executeService) {
        this.userService = userService;
        this.jaxbService = jaxbService;
        this.executeService = executeService;
    }

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

    @SneakyThrows private void executeFOK() {
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

    public void testFOK() {
        createUsers();
        unmarshalTal();
        executeFOK();
    }

    @SneakyThrows private void executeLMT() {
        Stock stock = Engine.getStockBySymbol("GOOGL");
        executeService.executeOrder(stock,
                new Order(OrderDirection.SELL, OrderType.LMT, 20, 100, "tal"));
        executeService.executeOrder(stock,
                new Order(OrderDirection.SELL, OrderType.LMT, 10, 110, "tal"));
        executeService.executeOrder(stock,
                new Order(OrderDirection.SELL, OrderType.LMT, 10, 90, "tal"));
    }

    public void testLMT() {
        createUsers();
        unmarshalTal();
        executeLMT();
    }

    @SneakyThrows private void executeMKT() {
        Stock stock = Engine.getStockBySymbol("GOOGL");
        executeService.executeOrder(stock,
                new Order(OrderDirection.SELL, OrderType.LMT, 5, 110, "tal"));
        executeService.executeOrder(stock,
                new Order(OrderDirection.BUY, OrderType.LMT, 20, 80, "tal"));
        executeService.executeOrder(stock,
                new Order(OrderDirection.SELL, OrderType.LMT, 25, 95, "tal"));
        executeService.executeOrder(stock,
                new Order(OrderDirection.SELL, OrderType.LMT, 10, 85, "tal"));
    }

    public void testMKT() {
        createUsers();
        unmarshalTal();
        executeMKT();
    }

}
