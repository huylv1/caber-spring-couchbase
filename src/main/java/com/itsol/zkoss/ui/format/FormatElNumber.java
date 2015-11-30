/**
 * 
 */
package com.itsol.zkoss.ui.format;

import java.text.NumberFormat;

/**
 * @author huylv
 *
 */
public class FormatElNumber {
	public static String formatStock(double stock) {
        final NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format(stock);
    }
}
