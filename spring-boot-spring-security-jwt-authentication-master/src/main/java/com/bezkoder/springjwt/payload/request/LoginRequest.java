package com.bezkoder.springjwt.payload.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
	@NotBlank(message = "Email must not be blank !!")

  	private String email;

	@NotBlank(message = "Password must not be blank !!")
	private String password;
}
