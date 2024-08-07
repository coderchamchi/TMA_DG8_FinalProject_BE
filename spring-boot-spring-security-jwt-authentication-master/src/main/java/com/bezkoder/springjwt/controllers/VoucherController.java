package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.VoucherService;
import com.bezkoder.springjwt.dto.ResponseJson;
import com.bezkoder.springjwt.entities.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") long id) {
        Optional<Voucher> voucher = voucherService.getProductbyid(id);
        if (voucher.isEmpty()){
            return ResponseEntity.ok().body(new ResponseJson<>(HttpStatus.NOT_FOUND, "NOT FOUND"));
        }
        return ResponseEntity.ok().body(new ResponseJson<>(voucher, HttpStatus.ACCEPTED, "OK"));

    }
}
