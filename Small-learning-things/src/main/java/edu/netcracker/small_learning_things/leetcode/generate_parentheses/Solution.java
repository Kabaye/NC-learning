package edu.netcracker.small_learning_things.leetcode.generate_parentheses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author svku0919
 * @version 22.06.2023-13:02
 */

class Solution {
    public static List<String> generateParenthesis(int n) {
        if (n == 1) {
            return List.of("()");
        }

        List<Set<String>> arr = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            arr.add(new HashSet<>());
        }

        arr.get(0).add("()");

        for (int i = 1; i < n; i++) {
            Set<String> previous = arr.get(i - 1);
            Set<String> current = arr.get(i);

            for (String s : previous) {
                current.add("(" + s + ")");
                current.add("()" + s);
                current.add(s + "()");
            }
        }

        return arr.get(n - 1).stream().toList();
    }

    public static void main(String[] args) {
        List<String> strings = generateParenthesis(4);
        System.out.println(strings);

        Set<String> expected = Set.of("(((())))", "((()()))", "((())())", "((()))()", "(()(()))", "(()()())", "(()())()", "(())(())", "(())()()", "()((()))", "()(()())", "()(())()", "()()(())", "()()()()");
        for (String s : expected) {
            if (!strings.contains(s)) {
                System.out.println(s);
            }
        }
    }
}
