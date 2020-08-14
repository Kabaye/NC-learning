package edu.netcracker.cluster;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author svku0919
 * @version 29.07.2020
 */
public class ClusterMain {
    public static void main(String[] args) {

        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 0;
        System.out.println("Port: " + port);

        Config config = ConfigFactory
                .parseString("akka.remote.artery.canonical.port=" + port)
                .withFallback(ConfigFactory.load());

        //environment

        ActorSystem.create(environmentBehavior(), "ClusterSystem", config);
    }

    private static Behavior<Void> environmentBehavior() {
        return Behaviors.setup(actorContext -> {
            actorContext.spawn(Behaviors.setup(ClusterEventActor::new), "ClusterEventActor");
            return Behaviors.empty();
        });
    }
}
