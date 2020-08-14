package edu.netcracker.sharding.client;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;
import akka.cluster.sharding.typed.javadsl.ClusterSharding;
import akka.cluster.sharding.typed.javadsl.EntityTypeKey;
import edu.netcracker.sharding.actor.PuntoSwitcherActor;
import edu.netcracker.sharding.client.entity.Event;
import edu.netcracker.sharding.client.entity.PuntoSwitcherResponse;
import edu.netcracker.sharding.client.entity.Tick;
import edu.netcracker.sharding.entity.Command;
import edu.netcracker.sharding.entity.ConvertText;
import edu.netcracker.sharding.entity.ConvertedText;
import edu.netcracker.sharding.language.Languages;
import edu.netcracker.sharding.language.LanguagesPairs;
import edu.netcracker.sharding.utils.RandomStringUtils;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author svku0919
 * @version 12.08.2020
 */
public class PuntoSwitcherClient {
    public static final EntityTypeKey<Event> TYPE_KEY =
            EntityTypeKey.create(Event.class, "PuntoSwitcherClient");

    public static Behavior<Event> createClient(ActorSystem<?> actorSystem, String id) {
        return Behaviors.setup(context -> {
            ActorRef<ConvertedText> responseAdapter = context.messageAdapter(ConvertedText.class, PuntoSwitcherResponse::new);

            return Behaviors.receive(Event.class)
                    .onMessageEquals(Tick.TICK, () -> {
                        final Command text = createText(responseAdapter);
                        final int i = Integer.parseInt(id);
                        final int puntoSwitcherId = ThreadLocalRandom.current().nextInt(10) + 10 * i;
                        context.getLog().info("Sending to PuntoSwitcher with id: {}, came id: {}, text: {}", puntoSwitcherId, id, text);
                        ClusterSharding.get(actorSystem).entityRefFor(PuntoSwitcherActor.TYPE_KEY, Integer.toString(puntoSwitcherId))
                                .tell(text);
                        return Behaviors.same();
                    })
                    .onMessage(PuntoSwitcherResponse.class, response -> {
                        context.getLog().info("Result: {}", response.getText());
                        return Behaviors.same();
                    })
                    .build();
        });
    }

    private static Command createText(ActorRef<ConvertedText> replyTo) {
        final Languages lang = Languages.values()[ThreadLocalRandom.current().nextInt(Languages.values().length)];
        String text = RandomStringUtils.chars(lang)
                .limit(ThreadLocalRandom.current().nextInt(300))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return new ConvertText(text, Objects.equals(lang, Languages.EN) ? LanguagesPairs.EN_RU : LanguagesPairs.RU_EN, replyTo);
    }
}
