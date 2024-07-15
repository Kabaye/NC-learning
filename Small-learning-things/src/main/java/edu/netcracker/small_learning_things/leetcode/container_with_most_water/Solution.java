package edu.netcracker.small_learning_things.leetcode.container_with_most_water;

/**
 * @author svku0919
 * @version 15/07/2024-15:14
 */

public class Solution {
    public int maxArea(int[] height) {
        int i = 0, j = height.length - 1;
        int water = -1;
        while (i < j) {
            if (height[i] < height[j]) {
                water = Math.max(height[i] * (j - i), water);
                i++;
            } else {
                water = Math.max(height[j] * (j - i), water);
                j--;
            }
        }

        return water;
    }
}
