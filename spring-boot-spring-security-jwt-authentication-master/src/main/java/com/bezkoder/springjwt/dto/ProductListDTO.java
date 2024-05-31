package com.bezkoder.springjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ProductListDTO {
    private long idProduct;

    private String productName;

    private String productDescription;

    private String base64;

    private int price;

    private long idCategory;

    private int quantity;

}
