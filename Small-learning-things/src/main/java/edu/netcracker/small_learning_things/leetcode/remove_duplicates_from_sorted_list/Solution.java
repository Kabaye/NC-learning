package edu.netcracker.small_learning_things.leetcode.remove_duplicates_from_sorted_list;

/**
 * @author svku0919
 * @version 27/12/2023-14:24
 */

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

public class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }

        if (head.next == null) {
            return head;
        }

        ListNode curr = new ListNode(head.val);
        ListNode newHead = curr;
        int currVal = head.val;
        while (head.next != null) {
            if (head.next.val == currVal) {
                head = head.next;
            } else {
                curr.next = new ListNode(head.next.val);
                curr = curr.next;
                currVal = head.next.val;
                head = head.next;
            }
        }

        return newHead;
    }
}
