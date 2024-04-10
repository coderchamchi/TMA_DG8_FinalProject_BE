package com.bezkoder.springjwt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity

@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idUser")
  @JsonIgnore
  private Long userId;

  @Column(name = "fullName", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  @JsonIgnore
  private String password;

  @Column(name = "createDate")
  @JsonIgnore
  private LocalDate createdDate;

  @Column(name = "updateDate")
  @JsonIgnore
  private LocalDate updatedDate;

  @NaturalId
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name ="phoneNumber")
  private String phone;

  @Column(name = "address")
  private String address;

  @Column(name = "birthday")
  private LocalDate birthday;

  @Column(name="status")
  private int status;

  @ManyToMany(fetch = FetchType.LAZY) // lấy user thì lấy luôn quyền của nó
  @JoinTable( name = "userrole",
          joinColumns = @JoinColumn(name = "idUser"),
          inverseJoinColumns = @JoinColumn(name = "idRole")
  )

  private Set<Role> listRole = new HashSet<>();

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<ShoppingCart> shoppingCart = new ArrayList<>();
  // =new Arraylist<>() là để tránh tình trạng null exception khi thao tác với class shoppingcartitem mà chưa khởi tạo

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Payment> listPayment;

  //payment

  public User(String username, String encode, String email,LocalDate birthday) {
    this.username = username;
    this.password = encode;
    this.email = email;
    this.birthday=birthday;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}