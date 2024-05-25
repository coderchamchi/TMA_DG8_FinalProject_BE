package com.bezkoder.springjwt.dto;

import com.bezkoder.springjwt.entities.PaymentMethod;
import com.bezkoder.springjwt.entities.Voucher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PaymentDTO {
    private long voucher;

    private long paymentMethod;

    private String address;

    private int transportFee;
}
