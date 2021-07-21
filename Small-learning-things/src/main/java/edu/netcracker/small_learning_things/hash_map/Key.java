package edu.netcracker.small_learning_things.hash_map;

import java.util.Objects;

class Key {
    String key;

    Key(String key) {
        this.key = key;
    }

    @Override
    public int hashCode() {
        return key.charAt(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key1 = (Key) o;
        return Objects.equals(key, key1.key);
    }
}