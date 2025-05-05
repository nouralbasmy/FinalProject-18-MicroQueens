package com.example.payment.service;

import com.example.payment.command.PaymentCommand;
import com.example.payment.factory.PaymentFactory;
import com.example.payment.model.CODPayment;
import com.example.payment.model.CreditCardPayment;
import com.example.payment.model.Payment;
import com.example.payment.model.VFCashPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public void processPayment(Payment payment) {
        payment.processPayment();
    }

    public void processRefund(Payment payment) {
        payment.processRefund();
    }
}
