package com.bezkoder.springjwt.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "shoppingCart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idShoppingCart")
    private Long idShoppingCart;

    @Column(name="status")
    private int status;

    @Column(name = "createDate")
    private LocalDate createDate;

    @Column(name = "updateDate")
    private LocalDate updateDate;

    @ManyToOne(fetch = FetchType.EAGER)
    //FetchType.EAGER: Điều này có nghĩa là khi bạn lấy một đối tượng ShoppingCartItem từ cơ sở dữ liệu.
    // JPA cũng sẽ tự động lấy dữ liệu của User liên quan và đưa vào trong đối tượng ShoppingCartItem.
    // Điều này có thể làm tăng hiệu suất khi thường xuyên sử dụng thông tin của User liên quan và
    // không muốn phải thực hiện thêm câu truy vấn.
    @JoinColumn(name = "idUser")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "shoppingCart")
    @JsonIgnore
    private List<ShoppingCartItem> listShoppingCartItem;

    @OneToMany(mappedBy = "shoppingCart")
    @JsonIgnore
    private List<Payment> listPayment;

    public Long getIdShoppingCart() {
        return idShoppingCart;
    }

    public void setIdShoppingCart(Long idShoppingCart) {
        this.idShoppingCart = idShoppingCart;
    }
}