package edu.netcracker.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author svku0919
 * @version 25.04.2022-14:35
 */

@Data
@Accessors(chain = true)
public class SubEntity {
    @NotBlank(message = "notBlank subName subEntity")
    @Size(min = 2, message = "size subName subEntity")
    private String subName;
}
