package edu.netcracker.small_learning_things.leetcode.merge_sorted_array;

/**
 * @author svku0919
 * @version 26.06.2023-08:52
 */

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i1 = m - 1, i2 = n - 1, currPos = m + n - 1;

        while (i1 >= 0 || i2 >= 0) {
            if (i1 >= 0 && i2 >= 0) {
                if (nums1[i1] > nums2[i2]) {
                    nums1[currPos--] = nums1[i1--];
                } else {
                    nums1[currPos--] = nums2[i2--];
                }
            } else if (i1 >= 0) {
                nums1[currPos--] = nums1[i1--];
            } else {
                nums1[currPos--] = nums2[i2--];
            }
        }
    }
}
