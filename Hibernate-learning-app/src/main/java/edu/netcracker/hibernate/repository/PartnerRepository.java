package edu.netcracker.hibernate.repository;

import edu.netcracker.hibernate.entity.PartnerORM;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author svku0919
 * @version 14.10.2020
 */
public interface PartnerRepository extends JpaRepository<PartnerORM, Integer> {
}
