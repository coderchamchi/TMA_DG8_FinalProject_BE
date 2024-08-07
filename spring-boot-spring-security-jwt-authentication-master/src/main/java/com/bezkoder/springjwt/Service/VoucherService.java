package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.entities.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherService {
    List<Voucher> getAll();

    Optional<Voucher> getProductbyid(long id);

}
