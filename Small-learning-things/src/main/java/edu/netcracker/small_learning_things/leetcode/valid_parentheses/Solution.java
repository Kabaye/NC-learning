package edu.netcracker.small_learning_things.leetcode.valid_parentheses;

import java.util.Stack;

/**
 * @author svku0919
 * @version 19.06.2023-16:34
 */

class Solution {
    public static boolean isValid(String s) {
        if (s.length() == 1) {
            return false;
        }
        Stack<Character> openBrackets = new Stack<>();
        for (char c : s.toCharArray()) {
            if (isClosing(c)) {
                if (openBrackets.isEmpty()){
                    return false;
                }
                Character latest = switch (openBrackets.pop()) {
                    case '(' -> ')';
                    case '{' -> '}';
                    case '[' -> ']';
                    default -> throw new IllegalStateException("Unexpected value");
                };

                if (latest != c) {
                    return false;
                }
            } else {
                openBrackets.push(c);
            }
        }

        return openBrackets.isEmpty();
    }

    public static boolean isClosing(char c) {
        return c == ')' || c == '}' || c == ']';
    }

    public static void main(String[] args) {
        boolean valid = isValid("(]");
        System.out.println(valid);
    }
}
