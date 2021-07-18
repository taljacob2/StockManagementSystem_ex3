package com.team.web.ui.controller.ajax.stock;

import engine.Engine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j @Controller @RequestMapping("stock") public class AjaxStockController {

    /**
     * Responding {@code fragment} of <i>stocksTableList</i> to present all
     * stocks in the system.
     *
     * @return {@code fragment}.
     */
    @SneakyThrows @GetMapping @ResponseBody void getStock(
            @ModelAttribute("stockSymbol") String stockSymbol, Model model) {

        log.info("stockSymbol {}", stockSymbol); // DEBUG

        Engine.getStockBySymbol(stockSymbol);
        model.addAttribute("stock", Engine.getStockBySymbol(stockSymbol));
    }

    @GetMapping("transactions") public String getStockAdmin() {
        return "fragments/admin-stocks-table-fragment :: stocksAdminTableFragment";
    }



}
