package edu.netcracker.small_learning_things.leetcode.convert_sorted_array_to_binary_search_tree;

import java.util.Objects;

/**
 * @author svku0919
 * @version 04/01/2024-15:40
 */

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) {
            return null;
        }

        TreeNode node = switch (nums.length) {
            case 1 -> new TreeNode(nums[0]);
            case 2 -> new TreeNode(nums[0], null, new TreeNode(nums[1]));
            default -> null;
        };

        if (Objects.nonNull(node)) {
            return node;
        }
        int center = nums.length / 2;
        node = new TreeNode(nums[center]);
        node.left = sortedArrayToBST(nums, 0, center);
        node.right = sortedArrayToBST(nums, center + 1, nums.length);
        return node;
    }

    public TreeNode sortedArrayToBST(int[] nums, int left, int right) {
        if (right - left == 1) {
            return new TreeNode(nums[left]);
        }

        if (right - left == 2) {
            return new TreeNode(nums[left], null, new TreeNode(nums[left + 1]));
        }

        int center = (right - left) / 2 + left;
        TreeNode node = new TreeNode(nums[center]);

        node.left = sortedArrayToBST(nums, left, center);
        node.right = sortedArrayToBST(nums, center+1, right);
        return node;
    }
}
