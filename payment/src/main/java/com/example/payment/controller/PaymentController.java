package com.example.payment.controller;

import com.example.payment.command.PayCommand;
import com.example.payment.command.PaymentCommand;
import com.example.payment.factory.PaymentFactory;
import com.example.payment.model.Payment;
import com.example.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentFactory paymentFactory;
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentFactory paymentFactory, PaymentService paymentService)
    {
        this.paymentFactory = paymentFactory;
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public String pay(@RequestParam String paymentType, @RequestParam double amount, @RequestParam Long orderId, @RequestParam Long userId, @RequestParam String extraInfo) {

        // Create payment using the factory
        Payment payment = paymentFactory.createPayment(paymentType,amount,orderId,userId,extraInfo);
        PaymentCommand payCommand = new PayCommand(payment, paymentService);
        payCommand.execute();

        return "Payment initiated successfully";
    }

}