package com.team.web.ui.controller.signed;

import engine.Engine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import stock.Stocks;

@Controller @RequestMapping("signed") public class SignedController {

    /**
     * Show the <tt>/signed</tt> page.
     *
     * @param model
     * @return
     */
    @GetMapping public String signed(Model model) {
        Stocks stocks = Engine.getStocksForced();
        model.addAttribute("stocksList", stocks.getCollection());
        return "mainweb/signed";
    }

    /**
     * Responding {@code fragment} of <i>stocksTableList</i> to present all
     * stocks in the system.
     *
     * @return {@code fragment}.
     */
    @GetMapping("stocksList") public String getStocksList(Model model) {
        Stocks stocks = Engine.getStocksForced();
        model.addAttribute("stocksList", stocks.getCollection());
        return "fragments/stocks-table-fragment :: stocksTableFragment";
    }
}
