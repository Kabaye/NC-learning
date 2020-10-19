package edu.netcracker.hibernate.dao;

import edu.netcracker.hibernate.entity.ProductFamilyORM;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author svku0919
 * @version 16.10.2020
 */

@Repository
@Transactional
public class PartnerProductFamilyDao implements DAO<ProductFamilyORM, Integer> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductFamilyORM> saveAll(List<ProductFamilyORM> values) {
        for (ProductFamilyORM family : values) {
            save(family);
        }

        return values;
    }

    @Override
    public ProductFamilyORM save(ProductFamilyORM value) {
        entityManager.persist(value);
        return value;
    }

    @Override
    public ProductFamilyORM find(Integer id) {
        return entityManager.find(ProductFamilyORM.class, id);
    }

    @Override
    public ProductFamilyORM update(ProductFamilyORM value) {
        return entityManager.merge(value);
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(ProductFamilyORM.class, id));
    }

    @Override
    public List<ProductFamilyORM> findAll() {
        return entityManager.createNamedQuery("ProductFamilyORM.findAll", ProductFamilyORM.class).getResultList();
    }
}
