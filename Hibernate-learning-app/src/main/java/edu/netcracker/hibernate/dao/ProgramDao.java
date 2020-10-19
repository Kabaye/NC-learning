package edu.netcracker.hibernate.dao;

import edu.netcracker.hibernate.entity.ProgramORM;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author svku0919
 * @version 19.10.2020
 */
@Repository
@Transactional
public class ProgramDao implements DAO<ProgramORM, Integer> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProgramORM> saveAll(List<ProgramORM> values) {
        for (ProgramORM programORM : values) {
            save(programORM);
        }
        return values;
    }

    @Override
    public ProgramORM save(ProgramORM value) {
        entityManager.persist(value);
        return value;
    }

    @Override
    public ProgramORM find(Integer id) {
        return entityManager.find(ProgramORM.class, id);
    }

    @Override
    public ProgramORM update(ProgramORM value) {
        return entityManager.merge(value);
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(find(id));
    }

    @Override
    public List<ProgramORM> findAll() {
        return entityManager.createNamedQuery("ProgramORM.findAll", ProgramORM.class)
                .getResultList();
    }
}
