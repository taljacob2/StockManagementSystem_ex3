package com.team.shared.engine.data.collection;

/**
 * This <i>interface</i> indicates that the <i>implementing</i> {@code Object}
 * can calculate its periodic <i>price worth</i> = <tt>Period</tt>.
 *
 * @version 1.0
 */
@FunctionalInterface public interface Periodable {
    long getPeriod();
}
