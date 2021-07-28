package com.team.shared.engine.message.builder.err;

import com.team.shared.engine.message.builder.BuildMessage;

/**
 * This {@code class} is a sub-class in the message building process.
 * <p>This {@code class} in the parent class of all the {@code Error} messages
 * in the
 * building process.</p>
 *
 * @version 1.0
 * @see BuildMessage
 */
public class BuildError extends BuildMessage {

    public BuildError() {

        // append the message.
        stringBuilder.append("ERROR: ");
    }

}
