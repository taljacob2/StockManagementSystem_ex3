package com.team.web.ui.controller.signed;

import engine.Engine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import order.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import stock.Stock;
import stock.Stocks;
import timestamp.TimeStamp;

import java.util.List;

@Slf4j @RestController @RequestMapping("signed") public class SignedController {

    @GetMapping public ModelAndView signed(Model model) {

        // Show the 'signed' page:
        ModelAndView modelAndView = new ModelAndView();

        Stocks stocks = Engine.getStocksForced();
        List<Stock> stocksList = stocks.getCollection();
        model.addAttribute("stocksList", stocksList);

        modelAndView.setViewName("mainweb/signed");
        return modelAndView;
    }

    @SneakyThrows @GetMapping("{stockSymbol}")
    public ModelAndView linkToStock(@PathVariable String stockSymbol,
                                    Model model) {

        Stock stock = Engine.getStockBySymbol(stockSymbol);
        model.addAttribute("stock", stock);

        Order order = new Order();
        model.addAttribute("order", order);

        // Show the 'stock buy/sell' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/stock-order");
        return modelAndView;
    }

    @SneakyThrows @PostMapping("{stockSymbol}")
    public ModelAndView linkToStockPost(@PathVariable String stockSymbol,
                                        Order order, @ModelAttribute("username")
                                                String username, Model model) {



        order.setTimeStamp(TimeStamp.getTimeStamp());
        order.setRequestingUserName(username);

        log.info("direction {}", order.getOrderDirection());
        log.info("type {}", order.getOrderType());
        log.info("price {}", order.getDesiredLimitPrice());
        log.info("quantity {}", order.getQuantity());
        log.info("reached post {}", order);

        // Stock stock = Engine.getStockBySymbol(stockSymbol);
        // model.addAttribute("stock", stock);
        //

        // Engine.

        // Show the 'stock buy/sell' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/stock-order");
        return modelAndView;
    }

}
