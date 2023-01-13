package edu.netcracker.small_learning_things.string_to_int;

/**
 * @author svku0919
 * @version 13.01.2023-16:50
 */

public class Solution {
    public static int myAtoi(String s) {
        s = s.trim();

        if (s.isEmpty()) {
            return 0;
        }

        char toCheck = s.charAt(0);
        boolean hasSign = toCheck == '-' || toCheck == '+';
        char sign = hasSign ? toCheck : '+';

        int startNumIndex = hasSign ? 1 : 0;
        int endNumIndex = s.length();

        boolean onceFound = false;

        for (int i = startNumIndex; i < s.length(); i++) {
            if (s.charAt(i) != '0' && startNumIndex == (hasSign ? 1 : 0) && !onceFound) {
                startNumIndex = i;
                onceFound = true;
            }
            if (!Character.isDigit(s.charAt(i))) {
                endNumIndex = i;
                break;
            }
        }

        if (startNumIndex == endNumIndex || !onceFound) {
            return 0;
        }
        if (endNumIndex - startNumIndex > 10) {
            return sign == '+' ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }

        long l = Long.parseLong(s.substring(startNumIndex, endNumIndex)) * (sign == '+' ? 1 : -1);

        if (l > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (l < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }

        return (int) l;
    }

    public static void main(String[] args) {
        System.out.println(myAtoi("    0000000000000   "));
    }
}
