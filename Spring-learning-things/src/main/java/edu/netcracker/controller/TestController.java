package edu.netcracker.controller;

import edu.netcracker.model.Entity;
import edu.netcracker.service.EService;
import edu.netcracker.validation.annotation.IdConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author svku0919
 * @version 25.04.2022-14:37
 */

@Validated
@RestController
public class TestController {
    @Autowired
    private EService service;

    @PostMapping("/post")
    public Entity post(@RequestBody @IdConstraint @Valid Entity e) {
        return service.post(e);
    }

    @PutMapping("/put")
    public Entity put(@RequestBody @IdConstraint(idRequired = true) @Valid Entity e) {
        return service.put(e);
    }
}
