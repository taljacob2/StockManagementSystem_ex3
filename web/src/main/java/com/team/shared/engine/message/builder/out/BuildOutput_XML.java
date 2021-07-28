package com.team.shared.engine.message.builder.out;

import com.team.shared.engine.message.builder.BuildMessage;

/**
 * This {@code class} is a sub-class in the message building process.
 * <p>Represents a part of an {@code Output} message.</p>
 *
 * @version 1.0
 * @see BuildMessage
 * @see BuildOutput
 */
public class BuildOutput_XML extends BuildOutput {

    public BuildOutput_XML() {

        // append the message.
        stringBuilder.append("Your '.xml' file ");
    }
}
