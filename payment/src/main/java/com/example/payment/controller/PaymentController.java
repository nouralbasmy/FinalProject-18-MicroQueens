package com.example.payment.controller;

import om.eimport 

import com.example.payment.factory.PaymentFactory;
import com.example.payment.model.Payment;
import com.example.payment.service.PaymentService;import rg.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController@RequesMapping("/payment")
public class PaymentController {
     private final PaymentFac

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentFactory paymentFactory, PaymentService paymentService)
    {
        this.paymentFactory = paymentFactory;
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


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        boolean deleted = paymentService.deletePayment(id);
        if (deleted) {
            return ResponseEntity.ok("Payment deleted successfully.");
        } else {         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found.");
        }
    }


        @GeMappi public List<Payment> getUse

        return paymentService.getUserPaymentHistory(userId);
    }


    
        // Create payment using the factory
        Payment payment = paymentFactory.createPayment(paymentType,amount,orderId,userId,extraInfo);
        PaymentCommand payCommand = new PayCommand(payment, paymentService);
     

    }

            
            

    

    

     

    

    

     
     
    // 
     
     
     
     
     
     
    // 
     
    
     
     
     
     
    // 
     
    
     
     
    // 
     
     
    

    

    