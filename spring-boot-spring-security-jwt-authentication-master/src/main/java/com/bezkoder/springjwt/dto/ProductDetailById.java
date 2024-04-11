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

    private String productName;

    private String productDescription;

    private String base64;

    private List<Size> sizes;
}
