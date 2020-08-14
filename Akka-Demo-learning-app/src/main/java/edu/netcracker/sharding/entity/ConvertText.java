package edu.netcracker.sharding.entity;

import akka.actor.typed.ActorRef;
import edu.netcracker.sharding.language.LanguagesPairs;
import lombok.Data;

/**
 * @author svku0919
 * @version 12.08.2020
 */

@Data
public class ConvertText implements Command {
    private final String textForConversion;
    private final LanguagesPairs languagesPairs;
    private final ActorRef<ConvertedText> replyTo;
}
