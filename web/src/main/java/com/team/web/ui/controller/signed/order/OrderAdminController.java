package com.team.web.ui.controller.signed.order;

import application.pane.resources.afterexecutionsummary.container.AfterExecutionOrderAndTransactionContainer;
import engine.Engine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import main.MenuUI;
import order.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import stock.Stock;
import timestamp.TimeStamp;

@Slf4j @RestController @RequestMapping("orderAdmin") public class OrderAdminController {

    @SneakyThrows @GetMapping("{stockSymbol}")
    public ModelAndView linkToStock(@PathVariable String stockSymbol,
                                    Model model) {

        Stock stock = Engine.getStockBySymbol(stockSymbol);
        model.addAttribute("stock", stock);

        model.addAttribute("stockSymbol", stockSymbol);

        // Show the 'stock buy/sell' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/order-execution");
        return modelAndView;
    }

    @SneakyThrows @PostMapping("{stockSymbol}")
    public ModelAndView linkToStockPost(
            @PathVariable("stockSymbol") String stockSymbol, Order order,
            @ModelAttribute("requestingUserName") String username,
            Model model) {

        // Set the rest fields in the order:
        order.setTimeStamp(TimeStamp.getTimeStamp());
        order.setRequestingUserName(username);

        // Make a transaction order:
        MenuUI.command_EXECUTE_TRANSACTION_ORDER(
                new AfterExecutionOrderAndTransactionContainer(),
                Engine.getStockBySymbol(stockSymbol), order);

        // Show the 'stock buy/sell' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/order-execution-admin");
        return modelAndView;
    }

}
