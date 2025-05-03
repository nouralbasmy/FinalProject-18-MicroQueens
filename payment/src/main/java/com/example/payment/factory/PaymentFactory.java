package com.example.payment.factory;


import com.example.payment.model.CODPayment;
import com.example.payment.model.CreditCardPayment;
import com.example.payment.model.Payment;
import com.example.payment.model.VFCashPayment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFactory {

    public Payment createPayment(String paymentType, double amount, Long orderId, Long userId, String additionalInfo) {
        Payment payment = null;
        switch (paymentType.toUpperCase()) {
            case "COD":
                payment = new CODPayment(amount, orderId, userId, additionalInfo);  // billingAddress
                break;
            case "CREDIT_CARD":
                String[] cardDetails = additionalInfo.split(","); // assuming details are separated by commas
                payment = new CreditCardPayment(amount, orderId, userId, cardDetails[0], cardDetails[1], cardDetails[2]); // cardNumber, cardHolderName, expirationDate
                break;
            case "VFCASH":
                payment = new VFCashPayment(amount, orderId, userId, additionalInfo);  // mobileWalletNumber
                break;
            default:
                throw new IllegalArgumentException("Unknown payment type");
        }

        return payment;
    }
}
