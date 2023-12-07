package edu.netcracker.repository;

import edu.netcracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * @author svku0919
 * @version 07/12/2023-12:48
 */

public interface UserRepository extends JpaRepository<User, UUID> {
}
