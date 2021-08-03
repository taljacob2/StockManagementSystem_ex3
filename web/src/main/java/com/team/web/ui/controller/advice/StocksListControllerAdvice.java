package com.team.web.ui.controller.advice;

import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.engine.Engine;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This {@code class} represents a <b>global</b> configurator across all {@link
 * org.springframework.stereotype.Controller}s.
 */
@ControllerAdvice public class StocksListControllerAdvice {

    /**
     * Defines global {@link ModelAttribute} for all {@link
     * org.springframework.stereotype.Controller}s.
     *
     * @param model the next <tt>HTML</tt> model.
     */
    @ModelAttribute public void handleRequest(Model model) {

        // Populating "stocksList" in the model
        List<Stock> stocksList = Engine.getStocksForced().getCollection();
        model.addAttribute("stocksList", stocksList);

        // Populating "stocksSymbolList" in the model
        List<String> stocksSymbolList =
                stocksList.stream().map(Stock::getSymbol)
                        .collect(Collectors.toList());
        model.addAttribute("stocksSymbolList", stocksSymbolList);
    }
}
