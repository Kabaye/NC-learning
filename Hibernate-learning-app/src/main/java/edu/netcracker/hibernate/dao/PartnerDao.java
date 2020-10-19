package edu.netcracker.hibernate.dao;

import edu.netcracker.hibernate.entity.PartnerORM;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author svku0919
 * @version 14.10.2020
 */
@Repository
@Transactional
public class PartnerDao implements DAO<PartnerORM, Integer> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PartnerORM> saveAll(List<PartnerORM> values) {
        for (PartnerORM value : values) {
            save(value);
        }
        return values;
    }

    @Override
    public PartnerORM save(PartnerORM value) {
        entityManager.persist(value);
        return value;
    }

    @Override
    public PartnerORM find(Integer id) {
        return entityManager.find(PartnerORM.class, id);
    }

    @Override
    public PartnerORM update(PartnerORM value) {
//        entityManager.remove(entityManager.find(PartnerORM.class, value.getPartnerId()));
        final PartnerORM updatedValue = entityManager.merge(value);
        return updatedValue;
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(PartnerORM.class, id));
    }

    @Override
    public List<PartnerORM> findAll() {
        return entityManager.createNamedQuery("PartnerORM.findAll", PartnerORM.class)
                .getResultList();
    }
}
