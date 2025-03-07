package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
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
    List<Order> orders;

    @BeforeEach
    public void setUp() {
        paymentRepository = new PaymentRepository();

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF-20240306-AB123XYZ");

        payments = new ArrayList<>();
        Payment payment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.BankTransfer.getValue(), paymentData);
        payments.add(payment1);

        Payment payment2 = new Payment("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                PaymentMethod.BankTransfer.getValue(), paymentData);
        payments.add(payment2);

        Payment payment3 = new Payment("e334ef40-9eff-4da8-9487-8ee697ecbf1e",
                PaymentMethod.BankTransfer.getValue(), paymentData);
        payments.add(payment3);

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);

        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);

        Order order3 = new Order("e334ef40-9eff-4da8-9487-8ee697ecbf1e",
                products, 1708570000L, "Bambang Sudrajat");
        orders.add(order3);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(1);
        Order order = orders.get(1);
        Payment result = paymentRepository.save(payment, order);

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(payment.getStatus(), findResult.getStatus());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(1);
        Order order = orders.get(1);
        paymentRepository.save(payment, order);

        Payment newPayment = new Payment(payment.getId(), payment.getMethod(),
                payment.getPaymentData());
        Payment result = paymentRepository.save(newPayment, order);

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), findResult.getStatus());
    }

    @Test
    void testSavePaymentNull() {
        Payment payment = null;
        Order order = orders.get(1);
        Payment result = paymentRepository.save(payment, order);

        List<Payment> findAll = paymentRepository.findAllPayments();
        assertNull(result);
        assertEquals(0, findAll.size());
    }

    @Test
    void testSaveOrderNull() {
        Payment payment = payments.get(1);
        Order order = null;
        Payment result = paymentRepository.save(payment, order);

        List<Payment> findAll = paymentRepository.findAllPayments();
        assertNull(result);
        assertEquals(0, findAll.size());
    }

    @Test
    void testFindPaymentIfIdFound() {
        for (int i = 0 ; i < payments.size() ; i++) {
            Payment payment = payments.get(i);
            Order order = orders.get(i);
            paymentRepository.save(payment, order);
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getPaymentData(), findResult.getPaymentData());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
    }

    @Test
    void testFindPaymentIfIdNotFound() {
        for (int i = 0 ; i < payments.size() ; i++) {
            Payment payment = payments.get(i);
            Order order = orders.get(i);
            paymentRepository.save(payment, order);
        }

        Payment findResult = paymentRepository.findById("zzcz");
        assertNull(findResult);
    }

    @Test
    void testFindPaymentIfNull() {
        for (int i = 0 ; i < payments.size() ; i++) {
            Payment payment = payments.get(i);
            Order order = orders.get(i);
            paymentRepository.save(payment, order);
        }

        Payment findResult = paymentRepository.findById(null);
        assertNull(findResult);
    }

    @Test
    void testFindAllPayments() {
        for (int i = 0 ; i < payments.size() ; i++) {
            Payment payment = payments.get(i);
            Order order = orders.get(i);
            paymentRepository.save(payment, order);
        }

        List<Payment> paymentList = paymentRepository.findAllPayments();
        assertEquals(3, paymentList.size());
    }

    @Test
    void testFindOrderByPaymentIdIfIdValid() {
        for (int i = 0 ; i < payments.size() ; i++) {
            Payment payment = payments.get(i);
            Order order = orders.get(i);
            paymentRepository.save(payment, order);
        }

        Order order = paymentRepository.findOrderByPayment(payments.get(1).getId());
        assertEquals(orders.get(1).getId(), order.getId());
        assertEquals(orders.get(1).getAuthor(), order.getAuthor());
        assertEquals(orders.get(1).getStatus(), order.getStatus());
    }

    @Test
    void testFindOrderByPaymentIdIfIdNotValid() {
        for (int i = 0 ; i < payments.size() ; i++) {
            Payment payment = payments.get(i);
            Order order = orders.get(i);
            paymentRepository.save(payment, order);
        }

        Order order = paymentRepository.findOrderByPayment("test");
        assertNull(order);
    }

    @Test
    void testFindOrderByPaymentIdIfNull() {
        for (int i = 0 ; i < payments.size() ; i++) {
            Payment payment = payments.get(i);
            Order order = orders.get(i);
            paymentRepository.save(payment, order);
        }

        Order order = paymentRepository.findOrderByPayment(null);
        assertNull(order);
    }
}
