package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF-20240306-AB123XYZ");
    }

    @Test
    void testCreatePaymentEmptyPaymentData() {
        this.paymentData.clear();

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                    "BankTransfer", this.paymentData);
        });
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                    "InvalidMethod", this.paymentData);
        });
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "BankTransfer", this.paymentData);

        assertSame(this.paymentData, payment.getPaymentData());
        assertTrue(payment.getPaymentData().containsKey("bankName"));
        assertTrue(payment.getPaymentData().containsKey("referenceCode"));
        assertEquals("BCA", payment.getPaymentData().get("bankName"));
        assertEquals("REF-20240306-AB123XYZ", payment.getPaymentData().get("referenceCode"));
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals("BankTransfer", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
    }

    @Test
    void testCreatePaymentSuccessStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "BankTransfer", "SUCCESS", this.paymentData);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                    "BankTransfer", "INVALID_STATUS", this.paymentData);
        });
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "BankTransfer", this.paymentData);

        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "BankTransfer", this.paymentData);

        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("INVALID_STATUS"));
    }
}
