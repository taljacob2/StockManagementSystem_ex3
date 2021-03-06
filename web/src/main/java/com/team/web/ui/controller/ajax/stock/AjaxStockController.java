package com.team.web.ui.controller.ajax.stock;

import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.engine.Engine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j @Controller @RequestMapping("stock") public class AjaxStockController {

    @SneakyThrows @GetMapping(value = "{stockSymbol}") @ResponseBody
    public void getStock(@PathVariable("stockSymbol") String stockSymbol,
                            Model model) {
        Stock stock = Engine.getStockBySymbol(stockSymbol);

        // Additionally, set an attribute of the user's Role:
        model.addAttribute("stock", stock);
    }

}
