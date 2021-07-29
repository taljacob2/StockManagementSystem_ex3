package com.team.web.ui.controller.signed.order;

import com.team.shared.engine.data.order.Order;
import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.data.user.User;
import com.team.shared.engine.data.user.role.Role;
import com.team.shared.engine.engine.Engine;
import com.team.shared.engine.timestamp.TimeStamp;
import com.team.shared.model.notification.Notification;
import com.team.web.service.ExecuteService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@Slf4j @RestController @RequestMapping("order") public class OrderController {

    @Autowired ExecuteService executeService;

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
    public ModelAndView executeOrder(
            @PathVariable("stockSymbol") String stockSymbol, Order order,
            @ModelAttribute("requestingUserName") String username,
            RedirectAttributes redirectAttributes) {

        // Set the rest fields in the order:
        order.setTimeStamp(TimeStamp.getTimeStamp());
        order.setRequestingUserName(username);

        User user = Engine.findUserByNameForced(username);

        // Make a transaction order:
        Optional<Notification> optionalNotification = executeService
                .executeOrder(Engine.getStockBySymbol(stockSymbol), order);

        ModelAndView modelAndView = new ModelAndView("redirect:/signed/user");

        // If there is a notification, add it as an attribute:
        optionalNotification.ifPresent(notification -> {
            redirectAttributes.addFlashAttribute("notification", notification);
        });

        if (user.getRole() == Role.ADMIN) {
            modelAndView.setViewName("redirect:/signed/admin");
        }

        return modelAndView;
    }

}
