package com.team.web.ui.controller.signed.order;

import engine.Engine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import stock.Stock;

@Slf4j @RestController @RequestMapping("orderAdmin") public class OrderAdminController {

    @SneakyThrows @GetMapping("{stockSymbol}")
    public ModelAndView linkToStock(@PathVariable String stockSymbol,
                                    Model model) {

        Stock stock = Engine.getStockBySymbol(stockSymbol);
        model.addAttribute("stock", stock);

        model.addAttribute("stockSymbol", stockSymbol);

        // Show the 'stock buy/sell' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/order-execution-admin");
        return modelAndView;
    }

    @SneakyThrows @PostMapping("{stockSymbol}")
    public ModelAndView linkToStockPost(
            @PathVariable("stockSymbol") String stockSymbol,
            @ModelAttribute("requestingUserName") String username,
            Model model) {

        // Show the 'stock buy/sell' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/order-execution-admin");
        return modelAndView;
    }

}
