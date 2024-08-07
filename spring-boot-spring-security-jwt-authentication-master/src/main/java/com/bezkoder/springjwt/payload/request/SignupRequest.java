package com.bezkoder.springjwt.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SignupRequest {

  @Valid

  @Pattern(regexp = "^[a-zA-Z0-9.+_-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid Email Format (must be @gmail.com)")
//  @Email(message = "Invalid Email Format") //cái annotation này nhập nam@gmail, không có .com vẫn được, vì có những email dạng nam@email.vn
  @NotNull(message = "Error: Email is not found") //khong cho phep null nhung "" thi van duoc
  @NotBlank(message = "Error: Email is '' !!") //khong cho phep "" luon
  private String email;

  @NotBlank(message = "Username is Null !!")
  private String username;

  @NotBlank(message = "Birthday is Null !!")
  private String birthday;

  @NotBlank(message = "Password is Null !!")
  private String password;

//  @Pattern(regexp = "^\\d{10}$", message = "phone invalid format")
////  /d: 0->9, 10: -> length
//  private String phone;

  private Set<String> role;
}
