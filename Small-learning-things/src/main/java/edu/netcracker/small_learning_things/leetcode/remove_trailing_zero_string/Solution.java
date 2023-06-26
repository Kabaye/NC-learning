package edu.netcracker.small_learning_things.leetcode.remove_trailing_zero_string;

/**
 * @author svku0919
 * @version 26.06.2023-08:54
 */

class Solution {
    public String removeTrailingZeros(String num) {
        StringBuilder b = new StringBuilder();
        boolean nonLeadingZeros = true;
        int pos = num.length() - 1;

        while (nonLeadingZeros) {
            if (num.charAt(pos) != '0') {
                nonLeadingZeros = false;
            } else {
                pos--;
            }
        }

        return num.substring(0, pos + 1);
    }
}
