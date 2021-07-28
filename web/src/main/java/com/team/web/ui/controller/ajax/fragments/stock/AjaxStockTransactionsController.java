package com.team.web.ui.controller.ajax.fragments.stock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j @Controller @RequestMapping("ajax/fragments/stock/transactions")
public class AjaxStockTransactionsController {

    @GetMapping public String getStockAdmin() {
        return "fragments/users/stock/stock-transactions-table-fragment :: " +
                "stocksTransactionsTableFragment";
    }

}
