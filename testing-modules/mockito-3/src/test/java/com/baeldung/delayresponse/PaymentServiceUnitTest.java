package com.baeldung.delayresponse;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceUnitTest {

    @Mock
    PaymentService paymentService;

    @RegisterExtension
    static WireMockExtension wireMockRule = WireMockExtension.newInstance().build();

    @Test
    public void whenProcessingPayment_thenDelayResponseUsingThreadSleep(){
        when(paymentService.processPayment()).thenAnswer(invocation -> {
            Thread.sleep(2000); // Delay of 2 seconds
            return "SUCCESS";
        });

        long startTime = System.currentTimeMillis();
        String result = paymentService.processPayment();
        long endTime = System.currentTimeMillis();

        assertEquals("SUCCESS", result);
        assertTrue((endTime - startTime) >= 2000); // Verify the delay
    }

    @Test
    public void whenProcessingPayment_thenDelayResponseUsingAnswersWithDelay() throws Exception {

        when(paymentService.processPayment()).thenAnswer(AdditionalAnswers.answersWithDelay(2000, invocation -> "SUCCESS"));

        long startTime = System.currentTimeMillis();
        String result = paymentService.processPayment();
        long endTime = System.currentTimeMillis();

        assertEquals("SUCCESS", result);
        assertTrue((endTime - startTime) >= 2000); // Verify the delay
    }


    @Test
    public void whenProcessingPayment_thenDelayResponseUsingAwaitility() {

        when(paymentService.processPayment()).thenAnswer(invocation -> {
            Awaitility.await().pollDelay(2, TimeUnit.SECONDS).until(()->true);
            return "SUCCESS";
        });

        long startTime = System.currentTimeMillis();
        String result = paymentService.processPayment();
        long endTime = System.currentTimeMillis();

        assertEquals("SUCCESS", result);
        assertTrue((endTime - startTime) >= 2000); // Verify the delay
    }
}
