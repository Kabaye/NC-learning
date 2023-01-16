package edu.netcracker.small_learning_things.leetcode.add_two_numbers;

/**
 * Definition for singly-linked list.
 * @code {
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 *  }
 * }
 * @author svku0919
 * @version 16.01.2023-14:46
 */
public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode resultStart = new ListNode();
        ListNode previousDigit = resultStart;
        ListNode d1 = l1;
        ListNode d2 = l2;

        boolean addOneOnNextStep = false;
        int finalStepResult = d1.val + d2.val + (addOneOnNextStep ? 1 : 0);
        addOneOnNextStep = finalStepResult >= 10;
        previousDigit.val = finalStepResult % 10;

        d1 = d1.next;
        d2 = d2.next;

        while((d1 != null) || (d2 != null)) {
            int stepResult = 0;
            if (d1 != null && d2 != null) {
                stepResult = d1.val + d2.val;
                d1 = d1.next;
                d2 = d2.next;
            } else if (d1 != null) {
                stepResult = d1.val;
                d1 = d1.next;
            } else {
                stepResult = d2.val;
                d2 = d2.next;
            }
            finalStepResult = stepResult + (addOneOnNextStep ? 1 : 0);
            addOneOnNextStep = finalStepResult >= 10;
            previousDigit.next = new ListNode(finalStepResult % 10);
            previousDigit = previousDigit.next;
        }

        if (addOneOnNextStep) {
            previousDigit.next = new ListNode(1);
        }

        return resultStart;
    }

    static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
}
