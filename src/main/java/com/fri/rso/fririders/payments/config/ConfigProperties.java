package com.fri.rso.fririders.payments.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("payments-config")
public class ConfigProperties {

    @ConfigValue(watch = true)
    private boolean enablePaypal;

    @ConfigValue(watch = true)
    private boolean enableCreditCard;

    private boolean healthy;

    public boolean isEnablePaypal() {
        return enablePaypal;
    }

    public void setEnablePaypal(boolean enablePaypal) {
        this.enablePaypal = enablePaypal;
    }

    public boolean isEnableCreditCard() {
        return enableCreditCard;
    }

    public void setEnableCreditCard(boolean enableCreditCard) {
        this.enableCreditCard = enableCreditCard;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public String toJsonString() {
        return String.format(
                "{" +
                        "\"enablePaypal\": %b," +
                        "\"enableCreditCard\": %b," +
                        "\"healthy\": %b" +
                        "}",
                this.isEnablePaypal(),
                this.isEnableCreditCard(),
                this.isHealthy()
        );
    }
}
