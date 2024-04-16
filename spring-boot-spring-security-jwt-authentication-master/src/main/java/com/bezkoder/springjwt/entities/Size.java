package com.bezkoder.springjwt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity(name = "size")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idSize")
    private long idSize;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "idProduct")
    @JsonIgnore
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "SizeName")
    private ESize sizeName;

    @Column(name = "price")
    private int price;

    @Column(name = "updateDate")
    private LocalDate updateDate;

    @OneToMany(mappedBy = "size")
    @JsonIgnore
    private List<ShoppingCartItem> listShoppingCartItem;

//    @OneToMany(mappedBy = "size")
//    @JsonIgnore
//    private List<Payment> listPayment;

    public int getPrice() {
        return price;
    }
}
