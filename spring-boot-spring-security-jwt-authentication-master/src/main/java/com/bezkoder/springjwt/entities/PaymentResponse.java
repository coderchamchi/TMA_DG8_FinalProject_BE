package com.bezkoder.springjwt.entities;

import com.bezkoder.springjwt.dto.ProductListDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentResponse {
    private int voucher;

    private String paymentMethod;

    private String address;

    private int transportFee;

    private List<ProductListDTO> productListDTOS;

    private String fullName;

    private String phone;
}
