package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;
    List<Product> products;

    @BeforeEach
    public void setUp() {
        paymentRepository = new PaymentRepository();

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF-20240306-AB123XYZ");

        payments = new ArrayList<>();
        Payment payment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "BankTransfer", paymentData);
        payments.add(payment1);

        Payment payment2 = new Payment("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                "BankTransfer", paymentData);
        payments.add(payment2);

        Payment payment3 = new Payment("e334ef40-9eff-4da8-9487-8ee697ecbf1e",
                "BankTransfer", paymentData);
        payments.add(payment3);

        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(2);
        products.add(product1);
    }

    @Test
    void testAddPayment() {
        Payment payment = payments.get(1);
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d7892",
                products, 1708560000L, "Safira Sudrajat");
        Payment result = paymentRepository.addPayment(order, payment.getMethod(), payment.getPaymentData());

        Payment findResult = paymentRepository.getPayment(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
    }

    @Test
    void testAddPaymentUpdate() {
        Payment payment = payments.get(1);
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d7892",
                products, 1708560000L, "Safira Sudrajat");
        paymentRepository.addPayment(order, payment.getMethod(), payment.getPaymentData());

        Payment newPayment = new Payment(payment.getId(), payment.getMethod(),
                PaymentStatus.SUCCESS.getValue(), payment.getPaymentData());
        Payment result = paymentRepository.addPayment(order, newPayment.getMethod(), newPayment.getPaymentData());

        Payment findResult = paymentRepository.getPayment(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), findResult.getStatus());
    }

    @Test
    void testGetPaymentIfIdFound() {
        for (Payment payment : payments) {
            Order order = new Order("13652556-012a-4c07-b546-54eb1396d7892",
                    products, 1708560000L, "Safira Sudrajat");
            paymentRepository.addPayment(order, payment.getMethod(), payment.getPaymentData());
        }

        Payment findResult = paymentRepository.getPayment(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getPaymentData(), findResult.getPaymentData());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
    }

    @Test
    void testGetPaymentIfIdNotFound() {
        for (Payment payment : payments) {
            Order order = new Order("13652556-012a-4c07-b546-54eb1396d7892",
                    products, 1708560000L, "Safira Sudrajat");
            paymentRepository.addPayment(order, payment.getMethod(), payment.getPaymentData());
        }

        Payment findResult = paymentRepository.getPayment("zzcz");
        assertNull(findResult);
    }

    @Test
    void testGetAllPayments() {
        for (Payment payment : payments) {
            Order order = new Order("13652556-012a-4c07-b546-54eb1396d7892",
                    products, 1708560000L, "Safira Sudrajat");
            paymentRepository.addPayment(order, payment.getMethod(), payment.getPaymentData());
        }

        List<Payment> paymentList = paymentRepository.getAllPayments();
        assertEquals(3, paymentList.size());
    }

    @Test
    void testSetPaymentStatusToSuccess() {
        Payment payment = payments.get(1);
        Payment result = paymentRepository.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testSetPaymentStatusToRejected() {
        Payment payment = payments.get(2);
        Payment result = paymentRepository.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }
}
