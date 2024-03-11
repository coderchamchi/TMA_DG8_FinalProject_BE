package com.bezkoder.springjwt.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productname;

    private int price;

    private String description;

    private LocalDate created_at;

    private LocalDate updated_at;

    private int warehouse;

    private int discount;

    private Set<String> size;

    private String base64;

    private boolean deleted;

    private long category;
}

