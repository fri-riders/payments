package com.fri.rso.fririders.payments.config;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import java.util.logging.Logger;

public class ConfigurationEventHandler {

    private static final Logger log = Logger.getLogger(ConfigurationEventHandler.class.getName());

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        String enablePaypalWatchedKey = "payments-config.enable-paypal";
        String enableCreditCardWatchedKey = "payments-config.enable-credit-card";

        ConfigurationUtil.getInstance().subscribe(enablePaypalWatchedKey, (String key, String value) -> {
            if (enablePaypalWatchedKey.equals(key)) {
                if ("true".equals(value.toLowerCase())) {
                    log.info("PayPal is enabled.");
                } else {
                    log.info("PayPal is disabled.");
                }
            }
        });

        ConfigurationUtil.getInstance().subscribe(enableCreditCardWatchedKey, (String key, String value) -> {
            if (enableCreditCardWatchedKey.equals(key)) {
                if ("true".equals(value.toLowerCase())) {
                    log.info("Credit cards are enabled.");
                } else {
                    log.info("Credit cards are disabled.");
                }
            }
        });
    }

}
