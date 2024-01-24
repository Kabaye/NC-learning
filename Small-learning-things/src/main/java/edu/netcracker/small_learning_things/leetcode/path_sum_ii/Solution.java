package edu.netcracker.small_learning_things.leetcode.path_sum_ii;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author svku0919
 * @version 10.01.2024-12:18
 */

class Solution {
    List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        pathSumInner(root, new ArrayList<>(), targetSum);
        return result;
    }

    public void pathSumInner(TreeNode root, List<Integer> path, int targetSum) {
        if (root == null) {
            return;
        }

        path.add(root.val);

        if (root.left == null && root.right == null) {
            if (targetSum == root.val) {
                result.add(new ArrayList<>(path));
            }
        }

        pathSumInner(root.left, path, targetSum - root.val);
        pathSumInner(root.right, path, targetSum - root.val);

        path.remove(path.size() - 1);
    }

//    public static void main(String[] args) {
//        Integer[] values = new Integer[]{1};
//
//        TreeNode root = new TreeNode(2);
//
//        Queue<Integer> vals = new LinkedList<>(Arrays.asList(values));
//        Queue<TreeNode> nodes = new LinkedList<>();
//        nodes.add(root);
//
//        while (!vals.isEmpty()) {
//            TreeNode current = nodes.poll();
//            Integer left = vals.poll();
//            if (left != null) {
//                current.left = new TreeNode(left);
//                nodes.add(current.left);
//            }
//            Integer right = vals.poll();
//            if (right != null) {
//                current.right = new TreeNode(right);
//                nodes.add(current.right);
//            }
//        }
//
//        List<List<Integer>> lists = pathSum(root, 0);
//        System.out.println(lists);
//    }
}

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

