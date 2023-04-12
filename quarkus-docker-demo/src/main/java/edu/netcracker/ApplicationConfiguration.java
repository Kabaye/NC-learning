package edu.netcracker;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@StaticInitSafe
@ConfigMapping(prefix = "quarkus-docker-demo")
public interface ApplicationConfiguration {

    Integer defaultAmount();

}
