package com.example.payment.service;

import com.example.payment.model.Payment;
import com.example.payment.model.CODPayment;
import com.example.payment.model.CreditCardPayment;
import com.example.payment.model.VFCashPayment;
import com.example.payment.repository.PaymentRepository;
import com.example.payment.strategy.PaymentStrategy;
import com.example.payment.strategy.CODPaymentStrategy;
import com.example.payment.strategy.CreditCardPaymentStrategy;
import com.example.payment.strategy.VFCashPaymentStrategy;
import com.example.payment.model.PaymentFactory;
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

    public Payment createPayment(String paymentType, double amount, Long orderId, Long userId, String extraInfo) {
        String billingAddress = null;
        String mobileWalletNumber = null;
        String cardNumber = null;
        String cardHolderName = null;
        String expirationDate = null;


        switch (paymentType.toUpperCase()) {
            case "COD":
                billingAddress = extraInfo;
                break;
            case "VFCASH":
                mobileWalletNumber = extraInfo;
                break;
            case "CREDIT_CARD":
                String[] creditCardInfo = extraInfo.split(",");
                cardNumber = creditCardInfo[0];
                cardHolderName = creditCardInfo[1];
                expirationDate = creditCardInfo[2];
                break;
            default:
                throw new IllegalArgumentException("Invalid payment type");
        }

        return PaymentFactory.createPayment(paymentType, amount, orderId, userId, billingAddress, mobileWalletNumber, cardNumber, cardHolderName, expirationDate);
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

//    public boolean deletePayment(Long paymentId) {
//        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
//        if (optionalPayment.isPresent()) {
//            paymentRepository.delete(optionalPayment.get());
//            return true;
//        }
//        return false;
//    }

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

    public boolean processPayment(Payment payment) {
        try {
            //System.out.println("Processing payment of type: " + payment.getClass().getSimpleName());
            PaymentStrategy strategy = getPaymentStrategy(payment);
            strategy.process(payment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private PaymentStrategy getPaymentStrategy(Payment payment) {
        if (payment instanceof CODPayment) {
            return new CODPaymentStrategy();
        } else if (payment instanceof VFCashPayment) {
            return new VFCashPaymentStrategy();
        } else if (payment instanceof CreditCardPayment) {
            return new CreditCardPaymentStrategy();
        }
        throw new IllegalArgumentException("Invalid payment type");
    }
}