package com.hehe.wmb.util;

import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Locale;

@Component("formatHelper")
public class FormatHelper {
    public String rupiah(double amount) {
        Locale indonesia = new Locale("id", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(indonesia);
        format.setMaximumFractionDigits(0);
        return format.format(amount);
    }
}
