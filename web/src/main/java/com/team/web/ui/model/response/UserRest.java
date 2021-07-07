package com.team.web.ui.model.response;

/**
 * A <i>model</i> {@code class} which is used for converting an incoming
 * <tt>Java</tt> {@link Object} into a <tt>JSON</tt>.
 *
 * <blockquote>Note: in case there is restricted information in the
 * given {@link Object}, then the <i>response</i> <tt>JSON</tt> would
 * <i>not</i> reveal it publicly.
 * </blockquote>
 */
public class UserRest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
