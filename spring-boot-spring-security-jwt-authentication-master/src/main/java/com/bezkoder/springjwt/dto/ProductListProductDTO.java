package com.bezkoder.springjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ProductListProductDTO {
    private long idProduct;

    private long category;

    private String productName;

    private String productDescription;

    private String base64;

    private int status ;

    private LocalDate createDate;

    private LocalDate updateDate;

    private String sizeName;

}
