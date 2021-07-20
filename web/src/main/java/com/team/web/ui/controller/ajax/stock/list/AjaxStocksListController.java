package com.team.web.ui.controller.ajax.stock.list;

import engine.Engine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import stock.Stocks;

/**
 * Responding {@code fragment} of <i>stocksTableList</i> to present all stocks
 * in the system.
 */
@Controller @RequestMapping("ajax/stocksList")
public class AjaxStocksListController {

    @Controller @RequestMapping("ajax/stocksList/user")
    public static class AjaxStocksListUserController {

        @GetMapping public String getStocksListAdmin(Model model) {
            Stocks stocks = Engine.getStocksForced();
            model.addAttribute("stocksList", stocks.getCollection());
            return "fragments/admin-stocks-table-fragment :: stocksAdminTableFragment";
        }
    }

    @Controller @RequestMapping("ajax/stocksList/admin")
    public static class AjaxStocksListAdminController {

        @GetMapping public String getStocksListAdmin(Model model) {
            Stocks stocks = Engine.getStocksForced();
            model.addAttribute("stocksList", stocks.getCollection());
            return "fragments/admin-stocks-table-fragment :: stocksAdminTableFragment";
        }
    }

}
