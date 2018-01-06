package com.fri.rso.fririders.payments;

import com.kumuluz.ee.discovery.annotations.RegisterService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@RegisterService(value = "payments", ttl = 20, pingInterval = 15, environment = "dev", version = "1.0.0", singleton = false)
@ApplicationPath("v1")
public class PaymentsApplication extends Application {
}
