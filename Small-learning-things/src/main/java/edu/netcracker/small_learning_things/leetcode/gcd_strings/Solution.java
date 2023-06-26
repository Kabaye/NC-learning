package edu.netcracker.small_learning_things.leetcode.gcd_strings;

/**
 * @author svku0919
 * @version 26.06.2023-08:53
 */

class Solution {
    public String gcdOfStrings(String str1, String str2) {

        if (!(str1 + str2).equals(str2 + str1)) {
            return "";
        }

        int l1 = str1.length(), l2 = str2.length();
        int gcd = Math.min(l1, l2);

        while (l1 > 0 && l2 > 0) {
            if (l1 > l2) {
                l1 = l1 % l2;
            } else {
                l2 = l2 % l1;
            }
        }

        return str1.substring(0, l1 == 0 ? l2 : l1);
    }
}
