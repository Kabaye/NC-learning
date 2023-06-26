package edu.netcracker.small_learning_things.leetcode.search_insert;

/**
 * @author svku0919
 * @version 23.06.2023-16:11
 */

class Solution {
    public static int searchInsert(int[] nums, int target) {
        int start = 0;
        int end = nums.length;
        int curr = 0;
        while (end - start > 1) {
            curr = (end - start) / 2 + start;
            if (nums[curr] < target) {
                start = curr;
            } else if (nums[curr] > target) {
                end = curr;
            } else {
                return curr;
            }
        }

        if (nums[start] >= target) {
            return start;
        } else if (nums[start] < target) {
            return end;
        } else if (nums[end] >= target) {
            return end+1;
        }
        return curr;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 3, 5, 6};

        int i = searchInsert(arr, 5);
        System.out.println(i);
        int j = searchInsert(arr, 2);
        System.out.println(j);
        int k = searchInsert(arr, 7);
        System.out.println(k);
    }
}
