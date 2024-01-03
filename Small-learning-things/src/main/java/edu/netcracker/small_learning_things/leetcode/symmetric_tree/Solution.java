package edu.netcracker.small_learning_things.leetcode.symmetric_tree;

import java.util.Objects;

/**
 * @author svku0919
 * @version 03/01/2024-17:09
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
    public boolean isSymmetric(TreeNode root) {
        if (Objects.isNull(root)) {
            return true;
        }

        return isSymmetric(root.left, root.right);
    }

    public boolean isSymmetric(TreeNode left, TreeNode right) {
        if (Objects.isNull(left) && Objects.isNull(right)) {
            return true;
        }

        if (Objects.isNull(left)) {
            return false;
        }

        if (Objects.isNull(right)) {
            return false;
        }

        if (left.val != right.val) {
            return false;
        }

        if (isSymmetric(left.left, right.right)) {
            return isSymmetric(left.right, right.left);
        }

        return false;
    }
}
