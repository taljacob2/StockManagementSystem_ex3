package com.team.web.ui.controller.signed.order;

import com.team.shared.engine.data.order.Order;
import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.engine.Engine;
import com.team.shared.engine.engine.MenuUI;
import com.team.shared.engine.timestamp.TimeStamp;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Slf4j @RestController @RequestMapping("order") public class OrderController {

    @SneakyThrows @GetMapping("{stockSymbol}")
    public ModelAndView linkToStock(@PathVariable String stockSymbol,
                                    Model model) {

        Stock stock = Engine.getStockBySymbol(stockSymbol);
        model.addAttribute("stock", stock);

        model.addAttribute("stockSymbol", stockSymbol);

        model.addAttribute("order", new Order());

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

        log.info("order {}", order); // DEBUG

        // Make a transaction order:
        MenuUI.command_EXECUTE_TRANSACTION_ORDER(
                Engine.getStockBySymbol(stockSymbol), order);

        ModelAndView modelAndView = new ModelAndView("redirect:/signed/user");
        if (Engine.findUserByNameForced(username).getRole().toString()
                .equalsIgnoreCase("ADMIN")) {
            modelAndView.setViewName("redirect:/signed/admin");
        }
        return modelAndView;
    }

}
