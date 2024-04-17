package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.entities.Voucher;
import com.bezkoder.springjwt.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService{
    @Autowired
    private VoucherRepository voucherRepository;
    @Override
    public List<Voucher> getAll() {
        return voucherRepository.findAll();
    }
}
