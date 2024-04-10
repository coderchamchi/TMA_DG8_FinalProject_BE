package com.bezkoder.springjwt.dto;


import com.bezkoder.springjwt.entities.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ShoppingCartItemDTO {
    private int quantity;
    private int price;
    private Size size;
}
