package edu.netcracker.small_learning_things.leetcode.maximum_depth_of_binary_tree;

import java.util.Objects;

/**
 * @author svku0919
 * @version 04/01/2024-15:34
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
    public int maxDepth(TreeNode node) {
        if (Objects.isNull(node)) {
            return 0;
        }

        if (Objects.isNull(node.left) && Objects.isNull(node.right)) {
            return 1;
        }

        return 1 + Math.max(maxDepth(node.left), maxDepth(node.right));
    }
}
