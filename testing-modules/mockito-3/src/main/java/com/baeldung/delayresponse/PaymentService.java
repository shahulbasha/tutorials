package com.baeldung.delayresponse;

import java.util.concurrent.CompletableFuture;

public class PaymentService {

    public String processPayment(){
        // simulate processing payment and return completion status
        return "SUCCESS";
    }

    public CompletableFuture<String> processPaymentAsync() {
        // simulate processing payment and return completion status
        return CompletableFuture.completedFuture("SUCCESS");
    }
}
