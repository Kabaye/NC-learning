package edu.netcracker.sharding.settings;

import akka.actor.typed.ActorSystem;
import lombok.Data;

/**
 * @author svku0919
 * @version 13.08.2020
 */
@Data
public class PuntoSwitcherClientSettings {
    private final int clientsAmount;

    private PuntoSwitcherClientSettings(int clientsAmount) {
        this.clientsAmount = clientsAmount;
    }

    public static PuntoSwitcherClientSettings create(ActorSystem<?> system) {
        return new PuntoSwitcherClientSettings(system.settings().config().getConfig("punto.switcher").getInt("initial-clients"));
    }
}
