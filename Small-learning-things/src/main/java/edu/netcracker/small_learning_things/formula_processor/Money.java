package edu.netcracker.small_learning_things.formula_processor;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author svku0919
 * @version 08.10.2020
 */
@Data
@Accessors(chain = true)
public class Money {
    private Float amount;
    private Integer moneyMultiplier;
}
