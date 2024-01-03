package edu.netcracker.small_learning_things.test_small_things_here;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(getAmount("0"));
        System.out.println(getAmount("1"));
        System.out.println(getAmount("12"));
        System.out.println(getAmount("123"));
        System.out.println(getAmount("1230"));
        System.out.println(getAmount("9990"));
        System.out.println(getAmount("10000"));
    }

    public static String getAmount(String priceModificationValue) {
        if (Objects.isNull(priceModificationValue)
            || "0".equals(priceModificationValue)) {
            return "0";
        } else {
            return switch (priceModificationValue.length()) {
                case 1 -> "0.00" + priceModificationValue;
                case 2 -> "0.0" + priceModificationValue;
                case 3 -> "0." + priceModificationValue;
                default -> priceModificationValue.substring(0, priceModificationValue.length() - 3) + "."
                           + priceModificationValue.substring(priceModificationValue.length() - 3);
            };
        }
    }

}
