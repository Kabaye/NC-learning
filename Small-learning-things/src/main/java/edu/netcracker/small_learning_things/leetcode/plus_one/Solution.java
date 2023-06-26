package edu.netcracker.small_learning_things.leetcode.plus_one;

/**
 * @author svku0919
 * @version 26.06.2023-13:12
 */

class Solution {
    public static int[] plusOne(int[] digits) {
        int carry = 0;
        int i = digits.length - 1;
        digits[i] += 1;
        while (i >= 0 && digits[i] + carry >= 10) {
            digits[i] += carry;
            carry = 1;
            digits[i] -= 10;
            i--;
        }

        if (i >= 0 && carry > 0) {
            digits[i] += carry;
        }


        if (i < 0 && carry > 0) {
            int[] newDigits = new int[digits.length + 1];

            System.arraycopy(digits, 0, newDigits, 1, digits.length);
            newDigits[0] = 1;
            return newDigits;
        }

        return digits;
    }

    public static void main(String[] args) {
        int[] ints = plusOne(new int[]{9, 9});
        System.out.println(ints);
    }
}
