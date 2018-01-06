package com.fri.rso.fririders.payments.resource;

import com.fri.rso.fririders.payments.config.ConfigProperties;
import com.fri.rso.fririders.payments.entity.Jwt;
import com.fri.rso.fririders.payments.entity.Payment;
import com.fri.rso.fririders.payments.entity.User;
import com.fri.rso.fririders.payments.service.AuthService;
import com.fri.rso.fririders.payments.service.PaymentsService;
import com.fri.rso.fririders.payments.service.UserService;
import com.fri.rso.fririders.payments.util.Helpers;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.metrics.annotation.Metered;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("payments")
@Log
public class PaymentResource {

    private static final Logger log = LogManager.getLogger(PaymentResource.class.getName());

    @Inject
    private PaymentsService paymentsService;

    @Inject
    private UserService userService;

    @Inject
    private AuthService authService;

    @Inject
    private ConfigProperties configProperties;

    @GET
    @Metered(name = "get_payments")
    public Response getPayments() {
        return Response.ok(paymentsService.getPayments()).build();
    }

    @GET
    @Path("{paymentId}")
    @Metered(name = "get_payment")
    public Response getPayment(@Context HttpServletRequest request, @PathParam("paymentId") String paymentId) {
        Payment payment = paymentsService.findByPaymentId(paymentId);
        if (payment == null) {
            log.warn("Payment not found");
            return Response.status(Response.Status.NOT_FOUND).entity(Helpers.buildErrorJson("Payment not found.")).build();
        }

        String authToken = request.getHeader("authToken");
        if (authToken == null || authToken.equals("")) {
            log.warn("Auth token missing");
            return Response.status(Response.Status.UNAUTHORIZED).entity(Helpers.buildErrorJson("Auth token missing.")).build();
        }

        Jwt jwt = new Jwt();
        jwt.setToken(authToken);
        User user = userService.findUserById(jwt, payment.getUserId());
        if (user == null) {
            log.warn("User for payment not found.");
            return Response.status(Response.Status.NOT_FOUND).entity(Helpers.buildErrorJson("User for payment not found.")).build();
        }

        jwt.setEmail(user.getEmail());
        if (!authService.isTokenValid(jwt)) {
            log.warn("Invalid token.");
            return Response.status(Response.Status.UNAUTHORIZED).entity(Helpers.buildErrorJson("Invalid token.")).build();
        }

        return Response.ok(payment).build();
    }

    @POST
    @Metered(name = "make_payment")
    public Response makePayment(@Context HttpServletRequest request, Payment payment) {
        if (payment.isPaypalPayment() && !configProperties.isEnablePaypal()) {
            log.warn("PayPal payment is currently unavailable, please use other payment method.");
            return Response.status(Response.Status.FORBIDDEN).entity(Helpers.buildErrorJson("PayPal payment is currently unavailable, please use other payment method.")).build();
        }
        if (!payment.isPaypalPayment() && !configProperties.isEnableCreditCard()) {
            log.warn("Credit card payment is currently unavailable, please use other payment method.");
            return Response.status(Response.Status.FORBIDDEN).entity(Helpers.buildErrorJson("Credit card payment is currently unavailable, please use other payment method.")).build();
        }

        String authToken = request.getHeader("authToken");
        if (authToken == null || authToken.equals("")) {
            log.warn("Auth token missing");
            return Response.status(Response.Status.UNAUTHORIZED).entity(Helpers.buildErrorJson("Auth token missing")).build();
        }

        Jwt jwt = new Jwt();
        jwt.setToken(authToken);
        User user = userService.findUserById(jwt, payment.getUserId());
        if (user == null) {
            log.warn("User for payment not found.");
            return Response.status(Response.Status.NOT_FOUND).entity(Helpers.buildErrorJson("User for payment not found.")).build();
        }

        jwt.setEmail(user.getEmail());
        if (!authService.isTokenValid(jwt)) {
            log.warn("Invalid token.");
            return Response.status(Response.Status.UNAUTHORIZED).entity(Helpers.buildErrorJson("Invalid token.")).build();
        }

        payment.setUserId(user.getUuid());
        payment.setTransactionId(UUID.randomUUID().toString());
        Payment createdPayment = paymentsService.createPayment(payment);
        if (createdPayment == null) {
            log.warn("An error has occurred while executing payment.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Helpers.buildErrorJson("An error has occurred while executing payment.")).build();
        }

        return Response.ok(createdPayment).build();
    }
}
