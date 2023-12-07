package edu.netcracker.service;

import edu.netcracker.entity.User;

import java.util.List;

/**
 * @author svku0919
 * @version 07/12/2023-12:42
 */

public interface UserService {
    List<User> getAll();

    User save(User user);

    List<User> saveAll(List<User> users);
}
