package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.PaymentMethodService;
import com.bezkoder.springjwt.entities.PaymentMethod;
import com.bezkoder.springjwt.repository.PaymentMethodRepository;
import com.bezkoder.springjwt.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;


    @Override
    public List<PaymentMethod> getAll() {
        return paymentMethodRepository.findAll();
    }
}
