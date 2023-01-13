package edu.netcracker.small_learning_things.reverse_integer;

/**
 * @author svku0919
 * @version 13.01.2023-16:41
 */

public class Solution {
    public static int reverse(int x) {
        int reversed = 0;

        long checkOverflow = 0L;

        while (x != 0) {
            checkOverflow = reversed * 10L;
            if (checkOverflow > 0 ? checkOverflow > reversed * 10 : checkOverflow < reversed * 10) {
                return 0;
            }
            reversed = reversed * 10 + x % 10;
            x /= 10;
        }

        return reversed;
    }

    public static void main(String[] args) {
        System.out.println(reverse(-2147483648));
    }
}
