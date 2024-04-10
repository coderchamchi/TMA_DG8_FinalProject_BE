//package com.bezkoder.springjwt.payload.request;
//
//import com.bezkoder.springjwt.entities.Category;
//import com.bezkoder.springjwt.entities.Size;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.Type;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class ProductRequest {
//    private long idProduct;
//
//    private String productName;
//
//    private int price;
//
//    private int priceSale;
//
//    private String productDescription;
//
//    private Set<Size> listSize = new HashSet<>();
//
//    private String base64;
//
//    private boolean deleted;
//
//    private Category category;
//
//}
//
