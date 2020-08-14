package edu.netcracker.cluster;

import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;

/**
 * @author svku0919
 * @version 30.07.2020
 */
public class ClusterEventActor extends AbstractBehavior<ClusterEventActor.Event> {
    public ClusterEventActor(ActorContext<Event> context) {
        super(context);
        System.out.println("******************************************************************************************************************************");
        System.out.println("ClusterEventActor initialized with context: +" + context + "; actor system: " + context.getSystem() + "; self: " + context.getSelf() + ".");
        System.out.println("******************************************************************************************************************************");
    }

    @Override
    public Receive<Event> createReceive() {
        return newReceiveBuilder().onAnyMessage(event -> {
            getContext().getLog().info("Message: " + event);
            return this;
        }).build();
    }

    protected interface Event {
    }
}
