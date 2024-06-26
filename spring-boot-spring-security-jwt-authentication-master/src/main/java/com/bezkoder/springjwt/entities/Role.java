package com.bezkoder.springjwt.entities;

import javax.persistence.*;

@Entity
@Table(name = "Role")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name ="id")
  private int roleId;

  @Enumerated(EnumType.STRING)
  @Column(name = "RoleName")
  private ERole roleName;

  public ERole getRoleName() {
    return roleName;
  }

  public void setRoleName(ERole roleName) {
    this.roleName = roleName;
  }
}