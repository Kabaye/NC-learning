package edu.netcracker.sharding.actor;


import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.PreRestart;
import akka.actor.typed.Signal;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.cluster.sharding.typed.javadsl.ClusterSharding;
import akka.cluster.sharding.typed.javadsl.Entity;
import akka.cluster.sharding.typed.javadsl.EntityTypeKey;
import edu.netcracker.sharding.entity.Command;
import edu.netcracker.sharding.entity.ConvertText;
import edu.netcracker.sharding.entity.ConvertedText;
import edu.netcracker.sharding.storage.PuntoSwitcherStorage;

import java.util.Objects;

/**
 * @author svku0919
 * @version 11.08.2020
 */
public class PuntoSwitcherActor extends AbstractBehavior<Command> {
    public static final EntityTypeKey<Command> TYPE_KEY =
            EntityTypeKey.create(Command.class, "PuntoSwitcher");

    private final String entityId;

    public PuntoSwitcherActor(ActorContext<Command> context, String entityId) {
        super(context);
        this.entityId = entityId;
    }

    public static void init(ActorSystem<?> system, String role) {
        ClusterSharding.get(system)
                .init(Entity.of(TYPE_KEY, entityContext -> PuntoSwitcherActor.create(entityContext.getEntityId()))
                        .withRole(role));
    }

    private static Behavior<Command> create(String entityId) {
        return Behaviors.setup(context -> {
            context.getLog().info("Initializing PuntoSwitcher with entityId: {}", entityId);
            return new PuntoSwitcherActor(context, entityId);
        });
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(ConvertText.class, this::convertText)
                .onSignal(PostStop.class, this::postStop)
                .onSignal(PreRestart.class, this::preRestart)
                .build();
    }

    private Behavior<Command> preRestart(Signal signal) {
        getContext().getLog().info("PreRestart signal: {} for punto switcher: {}", signal, entityId);
        return Behaviors.same();
    }

    private Behavior<Command> postStop(Signal signal) {
        getContext().getLog().info("PostStop signal: {} for punto switcher: {}", signal, entityId);
        return Behaviors.same();
    }

    private Behavior<Command> convertText(ConvertText text) {
        StringBuilder stringBuilder = new StringBuilder(text.getTextForConversion().length());
        text.getTextForConversion().chars().forEach(i -> {
            final Character convertedSymbol = PuntoSwitcherStorage.getConvertedSymbol((char) i, text.getLanguagesPairs());
            stringBuilder.append(Objects.isNull(convertedSymbol) ? (char) i : convertedSymbol);
        });
        text.getReplyTo().tell(new ConvertedText(stringBuilder.toString(), text.getLanguagesPairs()));

        return Behaviors.same();
    }
}
