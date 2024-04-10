package com.bezkoder.springjwt.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SignupRequest {
  @NotBlank(message = "It's Null Bro !!")
  private String email;

  @NotBlank(message = "It's Null Bro !!")
  private String username;

  @NotBlank(message = "It's Null Bro !!")
  private String birthday;

  @NotBlank(message = "It's Null Bro !!")
  private String password;

  private Set<String> role;
}
