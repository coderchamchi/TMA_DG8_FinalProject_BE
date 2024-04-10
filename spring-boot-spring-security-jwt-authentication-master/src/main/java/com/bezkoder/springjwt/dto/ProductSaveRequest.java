package com.bezkoder.springjwt.dto;

import com.bezkoder.springjwt.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductSaveRequest {
    private long category;

    private String productName;

    private String productDescription;
}
