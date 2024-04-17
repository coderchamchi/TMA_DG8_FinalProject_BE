package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.PaymentMethodService;
import com.bezkoder.springjwt.dto.ResponseJson;
import com.bezkoder.springjwt.entities.PaymentMethod;
import com.bezkoder.springjwt.entities.PaymentResponse;
import com.bezkoder.springjwt.entities.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("ProjectSJ/PaymentMethod")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;
    @GetMapping("/all")
    public ResponseEntity<ResponseJson<List<PaymentMethod>>> getPaymentMethod(){
        List<PaymentMethod> paymentMethods = paymentMethodService.getAll();
        if(paymentMethods.isEmpty()){
            return ResponseEntity.ok().body(new ResponseJson<>("PaymentMethod isn't found" ));
        }
        return ResponseEntity.ok().body(new ResponseJson<>(paymentMethods, HttpStatus.ACCEPTED, "OK"));
    }
}
