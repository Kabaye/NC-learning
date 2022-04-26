package edu.netcracker.service;

import edu.netcracker.model.Entity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @author svku0919
 * @version 26.04.2022-14:43
 */

@Service
//@Validated
public class EService {
    public Entity post( Entity e){
        System.out.println("post " + e);
        return e;
    }

    public Entity put(Entity e){
        System.out.println("put " + e);
        return e;
    }
}
