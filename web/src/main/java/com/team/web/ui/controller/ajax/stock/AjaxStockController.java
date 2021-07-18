package com.team.web.ui.controller.ajax.stock;

import engine.Engine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import stock.Stock;

@Slf4j @Controller @RequestMapping("stock") public class AjaxStockController {

    /**
     * Responding {@code fragment} of <i>stocksTableList</i> to present all
     * stocks in the system.
     *
     * @return {@code fragment}.
     */
    @SneakyThrows @GetMapping(value = "{stockSymbol}") @ResponseBody
    public void getUserRole(@PathVariable("stockSymbol") String stockSymbol,
                            Model model) {
        Stock stock = Engine.getStockBySymbol(stockSymbol);

        // Additionally, set an attribute of the user's Role:
        model.addAttribute("stock", stock);
    }


    @GetMapping("transactions") public String getStockAdmin() {
        return "fragments/admin-stocks-table-fragment :: stocksAdminTableFragment";
    }


}
