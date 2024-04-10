package com.bezkoder.springjwt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "idProduct")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProduct;

    @ManyToOne(fetch=FetchType.LAZY)
    //  Nếu dùng LAZY thì đây là giá trị mặc định. Khi lấy một đối tượng Product, thông tin của Category sẽ không được lấy ngay lập tức
    //  dữ liệu của Catefory sẽ chỉ được lấy từ cơ sở dữ liệu khi bạn thực sự truy cập trường category trong đối tượng Product.
    @JoinColumn(name = "idCategory")
    @JsonIgnore
    private Category category;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productDescription")
    @Type(type = "text")
    private String productDescription;

    @Column(name="productImg")
    @Type(type = "text")
    private String base64;

    @Column(name="status")
    private int status ;

    @Column(name = "createDate")
    private LocalDate createDate;

    @Column(name = "updateDate")
    private LocalDate updateDate;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Size> listSize;

}
