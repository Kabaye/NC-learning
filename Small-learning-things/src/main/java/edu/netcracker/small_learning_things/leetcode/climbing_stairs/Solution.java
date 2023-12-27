package edu.netcracker.small_learning_things.leetcode.climbing_stairs;

/**
 * @author svku0919
 * @version 27/12/2023-14:41
 */

class Solution {
    public int climbStairs(int n) {
        if (n < 3) {
            return n;
        }
        int[] amount = new int[n+1];

        amount[1] = 1;
        amount[2] = 2;

        for (int i = 3; i <= n; i++) {
            amount[i] = amount[i - 1] + amount[i - 2];
        }

        return amount[n];
    }
}
