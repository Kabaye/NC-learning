package edu.netcracker.small_learning_things.leetcode.sqrtx;

/**
 * @author svku0919
 * @version 05.07.2023-10:13
 */

public class Solution {
    public static int mySqrt(int x) {
        int currentNum = x / 2;
        while (true) {
            long temp = (long)currentNum * currentNum;
            if ((long)(currentNum + 1) * (currentNum + 1) > x && temp <= x) {
                return currentNum;
            }
            if (temp > x) {
                currentNum /= 2;
            } else if (temp < x) {
                currentNum += 1;
            } else {
                return currentNum;
            }
        }
    }

    public static void main(String[] args) {
        int i = mySqrt(2147483647);

        System.out.println(i);
    }
}
