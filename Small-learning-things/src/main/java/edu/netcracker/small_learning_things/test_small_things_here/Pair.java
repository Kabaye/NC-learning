package edu.netcracker.small_learning_things.test_small_things_here;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author svku0919
 * @version 08.10.2020
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor(staticName = "of")
public class Pair<F, S> {
    private final F first;
    private final S second;
}
