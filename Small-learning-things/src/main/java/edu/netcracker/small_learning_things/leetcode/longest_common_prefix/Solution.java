package edu.netcracker.small_learning_things.leetcode.longest_common_prefix;

/**
 * @author svku0919
 * @version 16.01.2023-14:49
 */

public class Solution {
    public static Character[] symbols = new Character[1];

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0){
            return "";
        } else if (strs.length == 1) {
            return strs[0];
        }

        symbols = new Character[strs.length];

        int minSize = strs[0].length();

        for (int i = 0; i < strs.length; i++) {
            minSize = Math.min(strs[i].length(), minSize);
        }

        StringBuilder sb = new StringBuilder();

        int lastIndex = -1;
        for (int i = 0; i < minSize; i++) {
            if (!sameSymbol(strs, i)) {
                break;
            } else {
                sb.append(strs[0].charAt(i));
            }
        }

        return sb.toString();
    }

    private static boolean allSame() {
        boolean allSame = true;
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i] != symbols[0]) {
                allSame = false;
                break;
            }
        }
        return allSame;
    }

    public static boolean sameSymbol(String[] strs, int index) {
        for (int i = 0; i < strs.length; i++) {
            symbols[i] = strs[i].charAt(index);
        }

        return allSame();
    }
}
