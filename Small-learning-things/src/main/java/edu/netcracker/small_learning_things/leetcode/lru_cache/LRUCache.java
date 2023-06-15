package edu.netcracker.small_learning_things.leetcode.lru_cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author svku0919
 * @version 14.06.2023-09:16
 */

class LRUCache {
    static class Node {
        int key;
        int val;
        Node next;
        Node prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private final Map<Integer, Node> cache;
    private final Node leastUsed;
    private final Node lastUsed;
    private final int capacity;

    public LRUCache(int capacity) {
        cache = new HashMap<>((int) (capacity / 0.75f + 1));
        this.capacity = capacity;
        lastUsed = new Node(-1, -1);
        leastUsed = new Node(-2, -2);
        lastUsed.prev = leastUsed;
        leastUsed.next = lastUsed;
    }

    void toTop(Node node) {
        node.next = lastUsed;
        node.prev = lastUsed.prev;
        lastUsed.prev = node;
        node.prev.next = node;
    }

    void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public int get(int key) {
        Node currentNode = cache.get(key);
        if (!cache.containsKey(key)) {
            return -1;
        }
        remove(currentNode);
        toTop(currentNode);
        return currentNode.val;
    }

    public String put(int key, int value) {
        if (cache.containsKey(key)) {
            Node currentNode = cache.get(key);
            remove(currentNode);
            currentNode.val = value;
            toTop(currentNode);
        } else if (cache.size() >= capacity) {
            Node node = new Node(key, value);
            cache.remove(leastUsed.next.key);
            remove(leastUsed.next);
            toTop(node);
            cache.put(key, node);
        } else {
            Node node = new Node(key, value);
            toTop(node);
            cache.put(key, node);
        }
        return "null";
    }

    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */


    public static void main(String[] args) {
        String[] commands = "[[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]"
                .split("],\\[");
        String[] expectedArr = "null,null,null,1,null,-1,null,-1,3,4"
                .split(",");
        String size = commands[0].replaceAll("\\[", "").replaceAll("]", "");
        LRUCache lRUCache = new LRUCache(Integer.parseInt(size));
        for (int i = 1, splitLength = commands.length; i < splitLength; i++) {
            String command = commands[i].replaceAll("\\[", "").replaceAll("]", "");
            String result;
            if (command.contains(",")) {
                String[] putV = command.split(",");
                result = lRUCache.put(Integer.parseInt(putV[0]), Integer.parseInt(putV[1]));
            } else {
                result = String.valueOf(lRUCache.get(Integer.parseInt(command)));
            }
            String expected = expectedArr[i];
            if (!result.equals(expected)) {
                System.out.println("ERROR: " + command + "; item: " + i);
                return;
            }
        }

    }
}
