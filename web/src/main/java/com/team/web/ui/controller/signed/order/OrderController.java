package com.team.web.ui.controller.signed.order;

import application.pane.resources.afterexecutionsummary.container.AfterExecutionOrderAndTransactionContainer;
import com.team.web.shared.dto.StockDTO;
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

@Slf4j @RestController @RequestMapping("order") public class OrderController {

    @SneakyThrows @GetMapping("{stockSymbol}")
    public ModelAndView linkToStock(@PathVariable String stockSymbol,
                                    Model model) {

        Stock stock = Engine.getStockBySymbol(stockSymbol);
        model.addAttribute("stock", stock);

        model.addAttribute("stockDTO", new StockDTO());

        Order order = new Order();
        model.addAttribute("order", order);

        // Show the 'stock buy/sell' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/stock-order");
        return modelAndView;
    }

    @SneakyThrows @PostMapping public ModelAndView linkToStockPost(
            @ModelAttribute("stockDTO") StockDTO stockDTO, Order order,
            @ModelAttribute("requestingUserName") String username,
            Model model) {

        order.setTimeStamp(TimeStamp.getTimeStamp());
        order.setRequestingUserName(username);

        log.info("stockDTO {}", stockDTO);
        log.info("stockSymbol {}", stockDTO.getSymbol()); // DE-BUG

        MenuUI.command_EXECUTE_TRANSACTION_ORDER(
                new AfterExecutionOrderAndTransactionContainer(),
                Engine.getStockBySymbol(stockDTO.getSymbol()), order);


        // Show the 'stock buy/sell' page:
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/stock-order");
        return modelAndView;
    }

}
