package com.team.web.ui.controller.ajax.fragments.stock;

import com.team.shared.engine.engine.Engine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j @Controller
@RequestMapping("ajax/fragments/stock/{stockSymbol}/transactions")
public class AjaxStockTransactionsController {

    @SneakyThrows @GetMapping public String getStockTransactions(
            @PathVariable("stockSymbol") String stockSymbol, Model model) {
        model.addAttribute("stock", Engine.getStockBySymbol(stockSymbol));
        return "fragments/users/stock/stock-transactions-table-fragment :: stockTransactionsTableFragment";
    }

}
