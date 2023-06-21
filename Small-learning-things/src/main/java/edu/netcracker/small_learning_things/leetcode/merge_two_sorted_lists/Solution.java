package edu.netcracker.small_learning_things.leetcode.merge_two_sorted_lists;

/**
 * @author svku0919
 * @version 21.06.2023-14:39
 */

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head = null;
        ListNode curr = null;
        while (list1 != null || list2 != null) {
            if (list1 != null && list2 != null) {
                if (list1.val > list2.val) {
                    if (head == null) {
                        head = new ListNode(list2.val);
                        curr = head;
                    } else {
                        curr.next = new ListNode(list2.val);
                        curr = curr.next;
                    }
                    list2 = list2.next;
                } else {
                    if (head == null) {
                        head = new ListNode(list1.val);
                        curr = head;
                    } else {
                        curr.next = new ListNode(list1.val);
                        curr = curr.next;
                    }
                    list1 = list1.next;
                }
            } else if (list1 != null) {
                if (head == null) {
                    head = new ListNode(list1.val);
                    curr = head;
                } else {
                    curr.next = new ListNode(list1.val);
                    curr = curr.next;
                }
                list1 = list1.next;
            } else {
                if (head == null) {
                    head = new ListNode(list2.val);
                    curr = head;
                } else {
                    curr.next = new ListNode(list2.val);
                    curr = curr.next;
                }
                list2 = list2.next;
            }
        }


        return head;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));

        ListNode result = mergeTwoLists(l1, l2);
    }
}

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
