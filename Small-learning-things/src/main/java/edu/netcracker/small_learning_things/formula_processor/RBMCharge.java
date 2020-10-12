package edu.netcracker.small_learning_things.formula_processor;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * @author svku0919
 * @version 08.10.2020
 */
@Data
@Accessors(chain = true)
public class RBMCharge {
    private Money chargeCostMny;
    private Integer chargeType;
    private ZonedDateTime chargeEndDat;
}
