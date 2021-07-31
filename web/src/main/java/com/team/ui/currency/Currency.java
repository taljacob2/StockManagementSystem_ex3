package com.team.ui.currency;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * This {@code class} sets the print <i>format</i> for <i>currencies</i>.
 *
 * @version 1.0
 */
public class Currency {
    public static final DecimalFormat decimalFormat =
            new DecimalFormat("$#,##0.00;$-#,##0.00");

    private static final Locale locale = Locale.US;

    // public static final NumberFormat numberFormat =
    //         NumberFormat.getCurrencyInstance(locale);

    static {
        decimalFormat.setMaximumFractionDigits(0);
    }
}
