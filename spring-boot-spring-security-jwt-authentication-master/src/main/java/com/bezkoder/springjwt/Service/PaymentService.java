package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.dto.PaymentDTO;
import com.bezkoder.springjwt.entities.Payment;
import com.bezkoder.springjwt.entities.PaymentResponse;

import java.util.Map;

public interface PaymentService {
    boolean savePayment(long user, PaymentDTO paymentDTO);

    Payment updatePayment (long id, Map<String, Object> fields);

    PaymentResponse findById(long idShoppingCart);
}
