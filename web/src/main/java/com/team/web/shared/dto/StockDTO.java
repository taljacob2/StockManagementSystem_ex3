package com.team.web.shared.dto;

import java.io.Serializable;

public class StockDTO implements Serializable {

    private static final long serialVersionUID = 2758119451170036155L;

    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
