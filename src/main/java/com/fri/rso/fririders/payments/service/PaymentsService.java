package com.fri.rso.fririders.payments.service;

import com.fri.rso.fririders.payments.entity.Payment;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.metrics.annotation.Counted;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Log
public class PaymentsService {

    private static final Logger log = LogManager.getLogger(PaymentsService.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    public List<Payment> getPayments() {
        return entityManager.createNamedQuery("Payment.findAll", Payment.class).getResultList();
    }

    public Payment findByPaymentId(String paymentId) {
        try {
            return entityManager.createNamedQuery("Payment.findByPaymentId", Payment.class).setParameter("id", paymentId).getSingleResult();
        } catch (NoResultException e) {
            log.error(e.getMessage());

            return null;
        }
    }

    public List<Payment> findByBookingId(String bookingId) {
        try {
            return entityManager.createNamedQuery("Payment.findByBookingId", Payment.class).setParameter("id", bookingId).getResultList();
        } catch (NoResultException e) {
            log.error(e.getMessage());

            return new ArrayList<>();
        }
    }

    @Transactional
    @Counted(name = "create_payment_counter")
    public Payment createPayment(Payment payment) {
        try {
            beginTransaction();
            entityManager.persist(payment);
            commitTransaction();

            return payment;
        } catch (Exception e) {
            rollbackTransaction();

            log.error(e.getMessage());

            return null;
        }
    }

    private void beginTransaction() {
        if (!entityManager.getTransaction().isActive())
            entityManager.getTransaction().begin();
    }

    private void commitTransaction() {
        if (entityManager.getTransaction().isActive())
            entityManager.getTransaction().commit();
    }

    private void rollbackTransaction() {
        if (entityManager.getTransaction().isActive())
            entityManager.getTransaction().rollback();
    }
}
