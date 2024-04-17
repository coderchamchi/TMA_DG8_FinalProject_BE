package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.Impl.VoucherService;
import com.bezkoder.springjwt.Service.PaymentMethodService;
import com.bezkoder.springjwt.dto.ResponseJson;
import com.bezkoder.springjwt.entities.PaymentMethod;
import com.bezkoder.springjwt.entities.Voucher;
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
@RequestMapping("ProjectSJ/Voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;
    @GetMapping("/all")
    public ResponseEntity<ResponseJson<List<Voucher>>> getAllVoucher(){
        List<Voucher> vouchers = voucherService.getAll();
        if(vouchers.isEmpty()){
            return ResponseEntity.ok().body(new ResponseJson<>("Voucher isn't found" ));
        }
        return ResponseEntity.ok().body(new ResponseJson<>(vouchers, HttpStatus.ACCEPTED, "OK"));
    }
}
