package edu.netcracker.small_learning_things.leetcode.balanced_binary_tree;

import java.util.Objects;

/**
 * @author svku0919
 * @version 08/01/2024-09:40
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
    public boolean isBalanced(TreeNode node) {
        if (Objects.isNull(node)) {
            return true;
        }

        int left = height(node.left);
        if (left == -1) {
            return false;
        }

        int right = height(node.right);
        if (right == -1) {
            return false;
        }

        return Math.abs(left - right) <= 1;
    }

    public int height(TreeNode node) {
        if (Objects.isNull(node)) {
            return 0;
        }

        int left = height(node.left);
        if (left == -1) {
            return -1;
        }

        int right = height(node.right);
        if (right == -1) {
            return -1;
        }

        if (Math.abs(right - left) > 1) {
            return -1;
        } else {
            return Math.max(left, right) + 1;
        }
    }
}
