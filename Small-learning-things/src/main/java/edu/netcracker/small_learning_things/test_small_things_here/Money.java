package edu.netcracker.small_learning_things.test_small_things_here;

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
}
