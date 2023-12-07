package edu.netcracker.controller;

import edu.netcracker.entity.User;
import edu.netcracker.service.DefaultUserService;
import edu.netcracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author svku0919
 * @version 07/12/2023-12:40
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService interfaceService;
    private final DefaultUserService serviceWithoutInterface;

    @GetMapping("/interface")
    public List<User> getAll() {
        return interfaceService.getAll();
    }

    @PostMapping("/interface")
    public List<User> saveAll(@RequestBody List<User> users) {
        return interfaceService.saveAll(users);
    }

    @GetMapping("/without-interface")
    public List<User> getAll2() {
        return serviceWithoutInterface.getAll();
    }

    @PostMapping("/without-interface")
    public List<User> saveAll2(@RequestBody List<User> users) {
        return serviceWithoutInterface.saveAll(users);
    }
}
