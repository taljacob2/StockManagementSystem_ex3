package com.team.web.ui.controller.advice;

import engine.Engine;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import stock.Stocks;

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
        Stocks stocks = Engine.getStocksForced();
        model.addAttribute("stocksList", stocks.getCollection());
    }
}
