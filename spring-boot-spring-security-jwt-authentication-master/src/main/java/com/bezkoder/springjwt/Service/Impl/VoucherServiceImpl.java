package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.VoucherService;
import com.bezkoder.springjwt.entities.Voucher;
import com.bezkoder.springjwt.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;
    @Override
    public List<Voucher> getAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Optional<Voucher> getProductbyid(long id) {
        return voucherRepository.findById(id);
    }
}
