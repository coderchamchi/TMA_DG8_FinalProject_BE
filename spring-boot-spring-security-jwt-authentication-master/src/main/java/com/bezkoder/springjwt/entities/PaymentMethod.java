package com.bezkoder.springjwt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "paymentMethod")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idPaymentMethod")
    private long idPaymentMethod;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "paymentMethod")
    @JsonIgnore
    private List<Payment> listPayment;

    public long getIdPaymentMethod() {
        return idPaymentMethod;
    }

    public void setIdPaymentMethod(long idPaymentMethod) {
        this.idPaymentMethod = idPaymentMethod;
    }
}
