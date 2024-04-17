package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.entities.PaymentMethod;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethod> getAll();
}
