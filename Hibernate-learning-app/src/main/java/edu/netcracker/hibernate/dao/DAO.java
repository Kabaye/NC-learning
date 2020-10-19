package edu.netcracker.hibernate.dao;

import java.util.List;

/**
 * @author svku0919
 * @version 14.10.2020
 */

public interface DAO<T, ID> {
    List<T> saveAll(List<T> values);

    T save(T value);

    T find(ID id);

    T update(T value);

    void delete(ID id);

    List<T> findAll();
}
