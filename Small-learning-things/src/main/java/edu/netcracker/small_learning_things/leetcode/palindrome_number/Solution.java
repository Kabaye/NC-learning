package edu.netcracker.small_learning_things.leetcode.palindrome_number;

/**
 * @author svku0919
 * @version 22.06.2023-15:34
 */

class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        } else if (x < 10) {
            return true;
        } else {
            int temp = x;
            int reversed = 0;
            while (temp > 0) {
                reversed = reversed * 10 + temp % 10;
                temp /= 10;
            }
            return reversed == x;
        }
    }
}
