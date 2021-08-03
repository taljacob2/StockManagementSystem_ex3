package com.team.shared.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A {@code class} which is used for data transfers between different layers in
 * the <tt>Spring</tt> framework.
 */
@Data public class CompanyDTO implements Serializable {

    private static final long serialVersionUID = -2373129124787550331L;

    private String companyName;
    private String symbol;
    private long quantity;
    private long worth;

}
