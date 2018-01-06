package com.fri.rso.fririders.payments.health;

import com.fri.rso.fririders.payments.config.ConfigProperties;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class PaymentsServiceHealthCheck implements HealthCheck {

    @Inject
    private ConfigProperties configProperties;

    @Override
    public HealthCheckResponse call() {
        if (configProperties.isHealthy()) {
            return HealthCheckResponse.named(PaymentsServiceHealthCheck.class.getSimpleName()).up().build();
        } else {
            return HealthCheckResponse.named(PaymentsServiceHealthCheck.class.getSimpleName()).down().build();
        }
    }

}
