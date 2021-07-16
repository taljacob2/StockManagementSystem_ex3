package com.team.web.ui.controller.signed;

import engine.Engine;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import stock.Stock;
import stock.Stocks;

import java.util.List;

@RestController @RequestMapping("signed") public class SignedController {

    @GetMapping public ModelAndView signed(Model model) {

        // Show the 'signed' page:
        ModelAndView modelAndView = new ModelAndView();

        Stocks stocks = Engine.getStocksForced();
        List<Stock> stocksList = stocks.getCollection();
        model.addAttribute("stocksList", stocksList);

        modelAndView.setViewName("mainweb/signed");
        return modelAndView;
    }

    /**
     * Responding {@code dataType:"application/json"}, of list of all stocks in
     * the system.
     *
     * @return {@link Stocks#getCollection()}.
     */
    @GetMapping(value = "stocksList", produces = "application/json")
    @ResponseBody public List<Stock> getStocksList() {

        Stocks stocks = Engine.getStocksForced();
        return stocks.getCollection();
    }
}
