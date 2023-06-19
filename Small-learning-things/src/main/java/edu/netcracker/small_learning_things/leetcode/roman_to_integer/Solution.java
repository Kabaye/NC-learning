package edu.netcracker.small_learning_things.leetcode.roman_to_integer;

/**
 * @author svku0919
 * @version 19.06.2023-15:48
 */

class Solution {
    public static int romanToInt(String s) {
        int result = 0;
        char[] charArray = s.toCharArray();
        int prev = 0;
        for (int i = charArray.length - 1; i >= 0; i--) {
            char symbol = charArray[i];

            int currVal = switch (symbol) {
                case 'M' -> 1000;
                case 'D' -> 500;
                case 'C' -> 100;
                case 'L' -> 50;
                case 'X' -> 10;
                case 'V' -> 5;
                case 'I' -> 1;
                default -> throw new IllegalStateException("Unexpected value: " + symbol);
            };
            if (prev > currVal) {
                result -= currVal;
            } else {
                result += currVal;
            }

            prev = currVal;

        }

        return result;
    }

    public static void main(String[] args) {
        int mcmxciv = romanToInt("MCMXCIV");
        System.out.println(mcmxciv);
    }
}
