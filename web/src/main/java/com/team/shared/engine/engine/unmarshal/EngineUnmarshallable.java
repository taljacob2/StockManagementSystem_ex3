package com.team.shared.engine.engine.unmarshal;

import com.team.shared.engine.data.stock.Stocks;
import com.team.shared.engine.data.user.User;
import com.team.shared.engine.data.xjc.generated.RseHoldings;
import com.team.shared.engine.data.xjc.generated.RseStocks;

public interface EngineUnmarshallable {

    /**
     * Adds {@link RseStocks} provided to all the {@link Stocks} in the system.
     * Note: if there are no {@link Stocks} in the system yet, then initialize
     * it and try again.
     *
     * @param rseStocks the provided {@link RseStocks} from the <tt>Jaxb
     *                  unmarshaller</tt>
     */
    default void addStocks(Stocks engineStocks, RseStocks rseStocks) {
        engineStocks.addStocks(rseStocks);
    }

    default void addUserHoldings(User user, RseHoldings rseHoldings) {
        user.addHoldings(rseHoldings);
    }

}
