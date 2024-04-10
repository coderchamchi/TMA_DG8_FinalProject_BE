package com.bezkoder.springjwt.dto;

import com.bezkoder.springjwt.entities.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductDetailById {
    private long idProduct;

    private long category;

    private String productName;

    private String productDescription;

    private String base64;

    private int status ;

    private LocalDate createDate;

    private LocalDate updateDate;

    private String sizeName;

    private int price;

    private List<Size> sizeList;
}
