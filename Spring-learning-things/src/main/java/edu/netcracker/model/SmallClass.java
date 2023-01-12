package edu.netcracker.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author svku0919
 * @version 12.01.2023-11:27
 */
@Data
@Accessors(chain = true)
public class SmallClass {
    private String test;
    private Map<String, Object> additionalParameters;
}
