package com.bezkoder.springjwt.dto;

import com.bezkoder.springjwt.entities.ESize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class SizeRequestDTO {
    private long product;

    private ESize sizeName;

    private int price;
}
