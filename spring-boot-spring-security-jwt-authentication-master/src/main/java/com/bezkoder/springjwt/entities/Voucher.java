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
@Setter
@Getter

@Entity
@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idVoucher")
    private long idVoucher;

    @Column(name = "description")
    private int description;

    @OneToMany(mappedBy = "voucher")
    @JsonIgnore
    private List<Payment> listPayment;

    public long getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(long idVoucher) {
        this.idVoucher = idVoucher;
    }
}
