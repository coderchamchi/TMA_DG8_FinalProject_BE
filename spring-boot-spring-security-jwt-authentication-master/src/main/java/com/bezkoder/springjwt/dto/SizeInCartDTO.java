package com.bezkoder.springjwt.dto;


import com.bezkoder.springjwt.entities.ESize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class SizeInCartDTO {
    private int idSize;

    private String sizeName;

    private int price;






}
