package com.team.shared.engine.engine.unmarshal;

import com.team.shared.engine.data.user.User;
import com.team.shared.engine.data.user.holding.item.Item;
import com.team.shared.engine.engine.Engine;

import java.io.IOException;

public interface EngineValidator {

    /**
     * @param uploadingUser the {@link User} who uploaded the <tt>.xml</tt> file
     *                      to be {@code unmarshalled}.
     * @throws IOException in case the {@code Engine} has encountered a problem,
     *                     this would mean the {@code Engine} is in-valid.
     */
    default void validate(User uploadingUser) throws IOException {
        for (Item item : uploadingUser.getHoldings().getCollection()) {
            if (!Engine.getStocksForced().getCollection()
                    .contains(item.getStock())) {

                /* Throws here in case of invalid Engine */
            }
        }
    }

}
