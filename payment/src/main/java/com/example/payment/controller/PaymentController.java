package com.example.payment.controller;

import com.example.payment.command.PayCommand;
import com.example.payment.command.PaymentCommand;
import com.example.payment.factory.CODPaymentFactory;
import com.example.payment.factory.CreditCardPaymentFactory;
import com.example.payment.factory.PaymentFactory;
import com.example.payment.factory.VFCashPaymentFactory;
import com.example.payment.model.CreditCardPayment;
import com.example.payment.model.Payment;
import com.example.payment.model.VFCashPayment;
import com.example.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService)
    {
        this.paymentService = paymentService;
    }

    @GetMapping("/allPayments")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }


    @GetMapping("/{paymentId}")
    public Payment getPaymentById(@PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
    }


    @PutMapping("/update/{paymentId}")
    public Payment updatePayment(@PathVariable Long paymentId,
                                 @RequestParam double amount,
                                 @RequestParam String extraInfo) {
        try {
            return paymentService.updatePayment(paymentId, amount, extraInfo);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found");
        }
    }

    @GetMapping("/order/{orderId}")
    public Payment getPaymentByOrderId(@PathVariable Long orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        if (payment != null) {
            return payment;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found for this order");
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        boolean deleted = paymentService.deletePayment(id);
        if (deleted) {
            return ResponseEntity.ok("Payment deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found.");
        }
    }


    @GetMapping("/user/{userId}")
    public List<Payment> getUserPaymentHistory(@PathVariable Long userId) {
        return paymentService.getUserPaymentHistory(userId);
    }


    @PostMapping("/pay")
    public Long pay(@RequestParam String paymentType, @RequestParam double amount, @RequestParam Long userId, @RequestParam String extraInfo) {
        Payment payment;
        Long orderId = null;
        switch (paymentType.toUpperCase()) {
            case "COD": {
                payment = new CODPaymentFactory().createPayment(amount, orderId, userId, extraInfo);
                break;
            }
            case "CREDIT_CARD": {
                payment = new CreditCardPaymentFactory().createPayment(amount, orderId, userId, extraInfo);
                break;
            }
            case "VFCASH": {
                payment = new VFCashPaymentFactory().createPayment(amount, orderId, userId, extraInfo);
                break;
            }
            default: throw new IllegalArgumentException("Invalid payment type");
        }
        paymentService.processPayment(payment);
//        return "Payment initiated successfully";
        return payment.getId();
    }


    @PutMapping("/updateOrderId/{paymentId}")
    public void setPaymentOrderId(@PathVariable Long paymentId, @RequestParam Long orderId)
    {
        paymentService.setPaymentOrderId(paymentId, orderId);
    }

    @PostMapping("/refund/{orderId}/{amount}")
    public String refund(@PathVariable Long orderId, @RequestParam Long userId, @RequestParam double amount) {
           paymentService.processRefund(userId,amount,orderId);
           return "Refund processed successfully. The amount has been added to your wallet.";
    }


}