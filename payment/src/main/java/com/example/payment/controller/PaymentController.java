package com.example.payment.controller;

import com.example.payment.model.Payment;
import com.example.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("/addPayment")
    public Payment createPayment(@RequestParam String paymentType,
                                 @RequestParam double amount,
                                 @RequestParam Long orderId,
                                 @RequestParam Long userId,
                                 @RequestParam String extraInfo) {
        Payment payment = paymentService.createPayment(paymentType, amount, orderId, userId, extraInfo);
        return paymentService.save(payment);
    }

//    @PostMapping("/addPayment")
//    public Payment createPayment(@RequestBody Payment payment) {
//        return paymentService.save(payment);
//    }




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


    @GetMapping("/order/{orderId}")
    public Payment getPaymentByOrderId(@PathVariable Long orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        if (payment != null) {
            return payment;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found for this order");
        }
    }


    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody Payment payment) {
        boolean result = paymentService.processPayment(payment);
        if (result) {
            return ResponseEntity.ok("Payment processed successfully.");
        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment processing failed.");
        }
    }


//    @PostMapping("/process")
//    public ResponseEntity<String> processPayment(@RequestParam String paymentType,
//                                                 @RequestParam double amount,
//                                                 @RequestParam Long orderId,
//                                                 @RequestParam Long userId,
//                                                 @RequestParam String extraInfo) {
//        try {
//            Payment payment = paymentService.createPayment(paymentType, amount, orderId, userId, extraInfo);
//            boolean result = paymentService.processPayment(payment);
//
//            if (result) {
//                return ResponseEntity.ok("Payment processed successfully.");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment processing failed.");
//            }
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input or payment type.");
//        }
//    }
}