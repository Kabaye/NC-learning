package edu.netcracker.sharding.client.entity;

import edu.netcracker.sharding.entity.ConvertedText;
import lombok.Data;

/**
 * @author svku0919
 * @version 12.08.2020
 */

@Data
public class PuntoSwitcherResponse implements Event {
    private final ConvertedText text;
}
