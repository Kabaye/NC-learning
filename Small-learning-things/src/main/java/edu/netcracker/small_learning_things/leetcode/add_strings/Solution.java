package edu.netcracker.small_learning_things.leetcode.add_strings;

/**
 * @author svku0919
 * @version 20.06.2023-14:12
 */

class Solution {

    public static final char ZERO = '0';

    public static String addStrings(String num1, String num2) {
        int i = num1.length() - 1;
        int j = num2.length() - 1;

        char[] n1 = num1.toCharArray();
        char[] n2 = num2.toCharArray();

        StringBuilder result = new StringBuilder();

        int rememberOne = 0;
        while (i >= 0 || j >= 0) {
            if (i >= 0 && j >= 0) {
                int res = (n1[i] - ZERO) + ((n2[j] - ZERO)) + rememberOne;

                rememberOne = 0;

                if (res >= 10) {
                    rememberOne = 1;
                    res = res % 10;
                }

                result.append((char) (res + ZERO));
                i--;
                j--;
            } else {
                int res;
                if (i >= 0) {
                    res = (n1[i] - ZERO) + rememberOne;
                    rememberOne = 0;
                    if (res >= 10) {
                        rememberOne = 1;
                        res = res % 10;
                    }
                    result.append((char) (res + ZERO));
                    i--;
                } else {
                    res = (n2[j] - ZERO) + rememberOne;
                    rememberOne = 0;
                    if (res >= 10) {
                        rememberOne = 1;
                        res = res % 10;
                    }
                    result.append((char) (res + ZERO));
                    j--;
                }
            }
        }

        if (rememberOne == 1){
            result.append('1');
        }

        return result.reverse().toString();
    }


    public static void main(String[] args) {
        String s = addStrings("1", "9");
        System.out.println(s);
    }
}
