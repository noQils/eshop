package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Map<String, String> voucherPaymentData;
    private Map<String, String> bankTransferPaymentData;

    @BeforeEach
    void setUp() {
        this.voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP1234ABC5678");

        this.bankTransferPaymentData = new HashMap<>();
        bankTransferPaymentData.put("bankName", "BCA");
        bankTransferPaymentData.put("referenceCode", "REF-20240306-AB123XYZ");
    }

    @Test
    void testCreatePaymentEmptyPaymentData() {
        this.bankTransferPaymentData.clear();

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                    PaymentMethod.BankTransfer.getValue(), this.bankTransferPaymentData);
        });
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                    "InvalidMethod", this.bankTransferPaymentData);
        });
    }

    @Test
    void testCreatePayment() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VoucherCode.getValue(), this.voucherPaymentData);

        assertSame(this.voucherPaymentData, payment.getPaymentData());
        assertTrue(payment.getPaymentData().containsKey("voucherCode"));
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
        assertEquals(PaymentMethod.VoucherCode.getValue(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BankTransfer.getValue(), this.bankTransferPaymentData);

        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BankTransfer.getValue(), this.bankTransferPaymentData);

        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BankTransfer.getValue(), this.bankTransferPaymentData);

        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("INVALID_STATUS"));
    }

    @Test
    void testValidVoucherCode() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VoucherCode.getValue(), this.voucherPaymentData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeNotStartedWithESHOP() {
        this.voucherPaymentData.put("voucherCode", "BREAD1234ABC5678");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VoucherCode.getValue(), this.voucherPaymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeNot16Characters() {
        this.voucherPaymentData.put("voucherCode", "ESHOP1234ABC567");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VoucherCode.getValue(), this.voucherPaymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherCodeWithout8NumericCharacters() {
        this.voucherPaymentData.put("voucherCode", "ESHOP12H4ABC5678");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VoucherCode.getValue(), this.voucherPaymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherWithEmptyVoucherCode() {
        this.voucherPaymentData.put("voucherCode", "");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VoucherCode.getValue(), this.voucherPaymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}
