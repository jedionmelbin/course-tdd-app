package com.xprotec.app.domain;

import java.util.Date;

public class Checkout extends Cart {
    private Float paymentAmount = 0F;
    private Float paymentDue= 0F;
    private Date paymentDate;

    public enum PaymentStatus{
        DUE, DONE,
    };
    private PaymentStatus paymentStatus;

    public void  pay(Float payment){
        paymentAmount = payment;
        paymentDue = getTotalAmount() - paymentAmount;
        paymentDate = new Date();
    }

    public void confirmOrder(){
        if (paymentDue==0.0F){
            System.out.println("Payment successful! Thank you for order.");
        }else if (paymentDue > 0){
            System.out.printf("Payment Failed! Remaning $%f needs to paid", paymentDue);
        }
    }

    public void complete(){
        printCartDetails();
        confirmOrder();
    }

    public void printCartDetails() {
    }

    public Float getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Float getPaymentDue() {
        return paymentDue;
    }

    public void setPaymentDue(Float paymentDue) {
        this.paymentDue = paymentDue;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
