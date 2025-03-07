package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.dataValidation(method, paymentData);
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setMethod(String method) {
        if (PaymentMethod.contains(method)) {
            this.method = method;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void dataValidation(String method, Map<String, String> paymentData) {
        this.setMethod(method);

        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.paymentData = paymentData;
        }

        boolean validationStatus = false;
        if (method.equals("VoucherCode")) {
            validationStatus = validateVoucherCode(paymentData);
        }
        if (validationStatus) {
            this.setStatus("SUCCESS");
        } else {
            this.setStatus("REJECTED");
        }
    }

    public boolean validateVoucherCode(Map<String, String> paymentData) {
        if (!paymentData.containsKey("voucherCode")) {
            return false;
        }
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null) {
            return false;
        } else if (voucherCode.length() != 16) {
            return false;
        } else if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int numericCount = 0;
        for (int i = 0; i < voucherCode.length(); i++) {
            if (Character.isDigit(voucherCode.charAt(i))) {
                numericCount++;
            }
        }

        return numericCount == 8;
    }
}