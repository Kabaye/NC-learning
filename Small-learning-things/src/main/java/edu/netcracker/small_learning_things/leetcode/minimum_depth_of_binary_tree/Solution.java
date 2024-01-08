package edu.netcracker.small_learning_things.leetcode.minimum_depth_of_binary_tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author svku0919
 * @version 08/01/2024-11:40
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
    // dfs
    public int minDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.right == null) {
            return minDepth(root.left) + 1;
        } else if (root.left == null) {
            return minDepth(root.right) + 1;
        }

        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    // bfs
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.right == null && root.left == null) {
            return 1;
        }

        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.offer(root.left);
        nodes.offer(root.right);
        int path = 1;

        while (true) {
            Queue<TreeNode> tempNodes = new LinkedList<>();
            while (!nodes.isEmpty()) {
                TreeNode node = nodes.poll();
                if (node != null) {
                    if (node.left != null) {
                        tempNodes.offer(node.left);
                    }
                    if (node.right != null) {
                        tempNodes.offer(node.right);
                    }
                    if (node.right == null && node.left == null) {
                        return path + 1;
                    }
                }
            }
            path++;
            nodes = tempNodes;
        }
    }
}
