package edu.netcracker.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

/**
 * @author svku0919
 * @version 25.04.2022-14:26
 */

@Data
@Accessors(chain = true)
public class Entity {
    private UUID id;

    @NotBlank(message = "name entity")
    private String name;
    @NotBlank(message = "data entity")
    private String data;

    @Valid
    private SubEntity entity;

    @Valid
    private List<SubEntity> entities;
}
