package com.fri.rso.fririders.payments.service;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.discovery.enums.AccessType;
import com.kumuluz.ee.fault.tolerance.annotations.CommandKey;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestScoped
@Log
public class BookingsService {

    private static final Logger log = LogManager.getLogger(BookingsService.class.getName());

    private Client http = ClientBuilder.newClient();

    @Inject
    @DiscoverService(value = "display-bookings", version = "*", environment = "dev", accessType = AccessType.DIRECT)
    private Optional<String> bookingsUrl;

    @CircuitBreaker
    @Fallback(fallbackMethod = "findBookingFallback")
    @CommandKey("http-find-booking")
    @Timeout(value = 5, unit = ChronoUnit.SECONDS)
    @Asynchronous
    public Object findBooking(String bookingId) {
        try {
            log.info("bookingsUrl = " + bookingsUrl);
            log.info("bookingsUrl.isPresent() = " + bookingsUrl.isPresent());

            if (bookingsUrl.isPresent()) {
                return http.target(this.bookingsUrl.get() + "/v1/bookings/" + bookingId)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<Object>() {});
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());

            return null;
        }
    }

    public Object findBookingFallback(String userId) {
        log.warn("findBookingFallback called");
        return null;
    }

}
