package com.team.web.ui.model.request;


/**
 * A <i>model</i> {@code class} which is used for converting incoming
 * <tt>JSON</tt> from the <i>client</i> into a <tt>Java</tt> {@link Object}.
 *
 * <blockquote>Note: may contain restricted information that should
 * <i>not</i> be revealed publicly.
 * </blockquote>
 */
public class UsesDetailRequestModel {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
