package com.bezkoder.springjwt.dto;


import com.bezkoder.springjwt.entities.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ShoppingCartItemDTO {

    @Valid
    @NotNull(message = "idSize not be null")
    private long size;

    @NotNull(message = "quantity not be null")
    private int quantity;


}
