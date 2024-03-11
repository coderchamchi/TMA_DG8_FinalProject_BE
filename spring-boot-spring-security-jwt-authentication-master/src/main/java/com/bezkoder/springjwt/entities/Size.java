package com.bezkoder.springjwt.entities;

import javax.persistence.*;

@Entity(name = "Size")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int sizeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "SizeName")
    private ESize sizeName;

    public ESize getSizeName() {
        return sizeName;
    }

    public void setSizeName(ESize sizeName) {
        this.sizeName = sizeName;
    }
}
