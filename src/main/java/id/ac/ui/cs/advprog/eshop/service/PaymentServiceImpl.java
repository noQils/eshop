package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @Autowired
    private PaymentServiceImpl(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    };

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, method, paymentData);

        return paymentRepository.save(payment, order);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (payment == null) {
            throw new NoSuchElementException();
        }

        Order order = paymentRepository.findOrderByPayment(payment.getId());
        if (status.equals(PaymentStatus.SUCCESS.getValue())) {
            payment.setStatus(PaymentStatus.SUCCESS.getValue());
            orderService.updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());
            return payment;
        } else if (status.equals(PaymentStatus.REJECTED.getValue())) {
            payment.setStatus(PaymentStatus.REJECTED.getValue());
            orderService.updateStatus(order.getId(), OrderStatus.FAILED.getValue());
            return payment;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAllPayments();
    }
}