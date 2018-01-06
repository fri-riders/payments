package com.fri.rso.fririders.payments.entity;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "payments")
@NamedQueries({
        @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
        @NamedQuery(name = "Payment.findByPaymentId", query = "SELECT p FROM Payment p WHERE p.uuid = :id"),
        @NamedQuery(name = "Payment.findByBookingId", query = "SELECT p FROM Payment p WHERE p.bookingId = :id")
})
@UuidGenerator(name = "idGenerator")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(generator = "idGenerator")
    private String uuid;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "booking_id", nullable = false)
    private int bookingId;

    @Column(name = "is_paypal_payment", nullable = false)
    private boolean isPaypalPayment;

    @Column(name = "amount", nullable = false, precision = 3)
    private float amount;

    @Column(name = "payment_date", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date paymentDate;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public boolean isPaypalPayment() {
        return isPaypalPayment;
    }

    public void setPaypalPayment(boolean paypalPayment) {
        isPaypalPayment = paypalPayment;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
