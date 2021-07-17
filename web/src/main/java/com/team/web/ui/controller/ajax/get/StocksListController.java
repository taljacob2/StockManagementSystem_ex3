package com.team.web.ui.controller.ajax.get;

import engine.Engine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import stock.Stocks;

@Controller @RequestMapping("stocksList") public class StocksListController {

    /**
     * Responding {@code fragment} of <i>stocksTableList</i> to present all
     * stocks in the system.
     *
     * @return {@code fragment}.
     */
    @GetMapping public String getStocksList(Model model) {
        Stocks stocks = Engine.getStocksForced();
        model.addAttribute("stocksList", stocks.getCollection());
        return "fragments/stocks-table-fragment :: stocksTableFragment";
    }

}
