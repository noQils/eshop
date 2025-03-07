package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderService orderService;

    List<Payment> payments;
    Order order;

    @BeforeEach
    void setUp() {
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

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(2);
        products.add(product1);

        order = new Order("13652556-012a-4c07-b546-54eb139d79b",
                products, 1708560000L, "Safira Sudajat");
    }

    @Test
    void testAddPayment() {
        Payment payment = payments.get(1);
        when(paymentRepository.save(any(Payment.class), any(Order.class))).thenReturn(payment);
        Payment result = paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData());

        assertEquals(payment.getId(), result.getId());
        verify(paymentRepository).save(any(Payment.class), eq(order));
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = payments.get(1);

        when(paymentRepository.findOrderByPayment(payment.getId())).thenReturn(order);
        when(orderService.updateStatus(anyString(), anyString())).thenReturn(order);

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals(payment.getId(), result.getId());
        assertEquals("SUCCESS", result.getStatus());
        verify(orderService, times(1)).updateStatus(order.getId(), "SUCCESS");
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = payments.get(1);

        when(paymentRepository.findOrderByPayment(payment.getId())).thenReturn(order);
        when(orderService.updateStatus(anyString(), anyString())).thenReturn(order);

        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertEquals(payment.getId(), result.getId());
        assertEquals("REJECTED", result.getStatus());
        verify(orderService, times(1)).updateStatus(order.getId(), "FAILED");
    }

    @Test
    void testSetStatusInvalidStatus() {
        Payment payment = payments.get(1);

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payment, "INVALID"));

        verify(paymentRepository, times(0)).save(any(Payment.class), any(Order.class));
    }

    @Test
    void testSetStatusInvalidPaymentObject() {
        Payment payment = null;

        assertThrows(NoSuchElementException.class,
                () -> paymentService.setStatus(payment, "SUCCESS"));
    }

    @Test
    void testGetPaymentIfIdFound() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentIfIdNotFound() {
        doReturn(null).when(paymentRepository).findById("test");
        assertNull(paymentService.getPayment("test"));
    }

    @Test
    void testGetAllPayments() {
        Payment payment = payments.get(1);
        doReturn(payments).when(paymentRepository).findAllPayments();

        List<Payment> results = paymentService.getAllPayments();
        for (int i = 0; i < results.size(); i++) {
            assertEquals(payments.get(i).getId(), results.get(i).getId());
        }
        assertEquals(3, results.size());
    }

}