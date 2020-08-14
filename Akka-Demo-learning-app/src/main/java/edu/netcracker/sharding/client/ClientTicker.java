package edu.netcracker.sharding.client;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;
import akka.cluster.sharding.typed.javadsl.ClusterSharding;
import edu.netcracker.sharding.client.entity.Event;
import edu.netcracker.sharding.client.entity.Tick;

import java.time.Duration;

/**
 * @author svku0919
 * @version 14.08.2020
 */
public class ClientTicker {
    public static Behavior<Event> create(ActorSystem<?> actorSystem, int tickerId) {
        return Behaviors.setup(context ->
                Behaviors.withTimers(timers -> {
                    timers.startTimerWithFixedDelay(Tick.TICK, Tick.TICK, Duration.ofSeconds(5));

                    return Behaviors.receive(Event.class)
                            .onMessageEquals(Tick.TICK, () -> {
                                actorSystem.log().info("Sending tick to client: {}", tickerId);
                                ClusterSharding.get(actorSystem)
                                        .entityRefFor(PuntoSwitcherClient.TYPE_KEY, Integer.toString(tickerId))
                                        .tell(Tick.TICK);
                                return Behaviors.same();
                            })
                            .build();
                })
        );
    }

}
