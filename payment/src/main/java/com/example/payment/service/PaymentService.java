package com.example.payment.service;

import com.example.payment.command.PayCommand;
import com.example.payment.command.PaymentCommand;
import com.example.payment.factory.PaymentFactory;
import com.example.payment.model.CODPayment;
import com.example.payment.model.CreditCardPayment;
import com.example.payment.model.Payment;
import com.example.payment.model.VFCashPayment;
import com.example.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    public Payment updatePayment(Long paymentId, double amount, String extraInfo) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setAmount(amount);
            // Update other fields based on extraInfo if necessary
            paymentRepository.save(payment);
            return payment;
        }
        return null;
    }

    public boolean deletePayment(Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Payment> getUserPaymentHistory(Long userId) {
        return paymentRepository.findAllByUserId(userId);
    }

    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public void processPayment(Payment payment) {
        PaymentCommand payCommand = new PayCommand(payment);
        payCommand.execute();
        //DECREMENTT INVENTORY CALL HENAAA
        paymentRepository.save(payment);
    }

    public void processRefund(Payment payment) {
        payment.processRefund();
    }
}
