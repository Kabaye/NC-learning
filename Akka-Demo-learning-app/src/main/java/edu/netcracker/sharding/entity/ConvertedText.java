package edu.netcracker.sharding.entity;

import edu.netcracker.sharding.language.LanguagesPairs;
import lombok.Data;

/**
 * @author svku0919
 * @version 12.08.2020
 */

@Data
public class ConvertedText implements Command {
    private final String text;
    private final LanguagesPairs langPair;
}
