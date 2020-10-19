package edu.netcracker.hibernate.repository;

import edu.netcracker.hibernate.entity.SettlementORM;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author svku0919
 * @version 14.10.2020
 */
public interface SettlementRepository extends JpaRepository<SettlementORM, Integer> {
}
