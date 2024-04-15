package com.bezkoder.springjwt.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SignupRequest {

  @Valid

  @Email(message = "Invalid Email Format")
  @NotNull(message = "Email is not found") //khong cho phep null nhung rong~ thi van duoc
  @NotBlank(message = "Email is Null !!") //khong cho phep rong~
  private String email;

  @NotBlank(message = "Username is Null !!")
  private String username;

  @NotBlank(message = "Birthday is Null !!")
  private String birthday;

  @NotBlank(message = "Password is Null !!")
  private String password;

  private Set<String> role;
}
