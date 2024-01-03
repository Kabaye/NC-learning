package edu.netcracker.small_learning_things.leetcode.same_tree;

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
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (Objects.isNull(p) && Objects.isNull(q)) {
            return true;
        }

        if (Objects.isNull(p)) {
            return false;
        }

        if (Objects.isNull(q)) {
            return false;
        }

        if (p.val != q.val) {
            return false;
        }

        boolean sameTree = isSameTree(p.left, q.left);
        if (sameTree) {
            return isSameTree(p.right, q.right);
        }

        return false;
    }
}
