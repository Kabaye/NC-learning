package edu.netcracker.sharding.system;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;
import akka.cluster.typed.Cluster;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import edu.netcracker.sharding.actor.PuntoSwitcherActor;
import edu.netcracker.sharding.client.PuntoSwitcherClient;
import edu.netcracker.sharding.settings.PuntoSwitcherClientSettings;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author svku0919
 * @version 12.08.2020
 */
public class PuntoSwitcherActorSystem {
    public static void main(String[] args) {
        startup(args[0], Integer.parseInt(args[1]));
    }

    private static void startup(String role, int port) {
        // Override the configuration of the port
        Map<String, Object> overrides = new HashMap<>();
        overrides.put("akka.remote.artery.canonical.port", port);
        overrides.put("akka.cluster.roles", Collections.singletonList(role));

        Config config = ConfigFactory.parseMap(overrides)
                .withFallback(ConfigFactory.load("sharding"));

        ActorSystem.create(RootBehavior.create(), "PuntoSwitcherSystem", config);
    }

    private static class RootBehavior {
        public static Behavior<Void> create() {
            return Behaviors.setup(context -> {
                Cluster cluster = Cluster.get(context.getSystem());

                if (cluster.selfMember().hasRole("switcher")) {
                    PuntoSwitcherActor.init(context.getSystem(), "switcher");
                } else if (cluster.selfMember().hasRole("client")) {
                    final int clientsAmount = PuntoSwitcherClientSettings.create(context.getSystem()).getClientsAmount();
                    PuntoSwitcherActor.init(context.getSystem(), "client");
                    for (int i = 0; i < clientsAmount; i++) {
                        context.spawn(PuntoSwitcherClient.createClient(context.getSystem(), Integer.toString(i)), "client-" + i);
                    }
                }
                return Behaviors.empty();
            });
        }
    }
}
