package com.bezkoder.springjwt.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


import com.bezkoder.springjwt.Service.Impl.UserDetailsServiceImpl;
import com.bezkoder.springjwt.Service.RoleService;
import com.bezkoder.springjwt.Service.ShoppingCartService;
import com.bezkoder.springjwt.Service.UserService;

import com.bezkoder.springjwt.dto.ResponseJson;
import com.bezkoder.springjwt.dto.UserDTO;
import com.bezkoder.springjwt.dto.updatePassword;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.springjwt.entities.ERole;
import com.bezkoder.springjwt.entities.Role;
import com.bezkoder.springjwt.entities.User;
import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.config.jwt.JwtUtils;
import com.bezkoder.springjwt.Service.Impl.UserDetailsImpl;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  UserRepository userRepository;
  @Autowired
  UserService userService;

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  RoleService roleService;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  ShoppingCartService shoppingCartService;

@PostMapping("/signin")
public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
{
  try {
    // Lấy thông tin người dùng từ cơ sở dữ liệu
    UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());
    if(ObjectUtils.isEmpty(user)){
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "User Not Found"));
    }
    // Kiểm tra mật khẩu đã nhập với mật khẩu đã mã hóa trong cơ sở dữ liệu
    if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "User Not Found, Password Wrong!"));
    }
    // ok hết rồi thì mình lấy thông tin để tạo ra JWT, tiện cho việc đăng nhập
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));


//    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);


    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(
            jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  } catch (AuthenticationException e) {
    return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "User Not Found"));
  }

}
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userService.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userService.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate birthday = LocalDate.parse(signUpRequest.getBirthday(),formatter);
    User user = new User(
            signUpRequest.getUsername(),
            encoder.encode(signUpRequest.getPassword()),
            signUpRequest.getEmail(),
            birthday);
    user.setUpdatedDate(LocalDate.now());
    user.setCreatedDate(LocalDate.now());
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleService.findByRoleName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    }
    else
    {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleService.findByRoleName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleService.findByRoleName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setListRole(roles);
    user.setStatus(1);
    userService.saveOrupdate(user);
    shoppingCartService.saveShoppingCart(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public <String, string> Map<String, String> handleValidationExceptions(MethodArgumentNotValidException e){

    Map<String, String> errors = new HashMap<>();

    e.getBindingResult().getAllErrors().forEach((error )->{
      String fieldName = (String) ((FieldError)error ).getField();
      String errorMessage = (String) error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return errors;
  }


  @PutMapping("/update/{id}")
  public ResponseEntity<ResponseJson<Boolean>> updateUser
          (@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {

      User user = userRepository.getReferenceById(id);
      user.setUpdatedDate(LocalDate.now());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      user.setBirthday(LocalDate.parse(userDTO.getBirthday(),formatter));
      user.setAddress(userDTO.getAddress());
      user.setPhone(userDTO.getPhone());
      userService.saveOrupdate(user);
      return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, "Update User Success"));
  }
  @GetMapping("/all")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<List<User>> getallUser(){
      List<User> listuser = userService.getalluser();
    return new ResponseEntity<List<User>>(listuser, HttpStatus.OK);
  }
  @GetMapping("/user")//thong tin tra ve FE
  public ResponseEntity<Object> getuser(){
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return new ResponseEntity<>(principal, HttpStatus.OK);
  }
  @GetMapping("/userinfo")//thong tin chi tiet, cac field co trong entity user
  public ResponseEntity<User> getinfouser(){
    User user = userService.findUserByUserName();
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }
  @GetMapping("listUsername")
  public List<String> getListUsername(){
    return userService.getAllUsername();
  }

  @GetMapping("listEmail")
  public List<String> getListEmail(){
    return userService.getAllEmail();
  }

  @PostMapping("forgotPassword")
  public ResponseEntity<?> updatePassword(@Valid @RequestBody updatePassword updatepassword) {
    UserDetails user = userDetailsService.loadUserByUsername(updatepassword.getEmail());

    if(ObjectUtils.isEmpty(user)) {
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Email Not Found"));
    }

    if(encoder.matches(updatepassword.getPassword(), user.getPassword())){
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "The new password must be different from the old password"));
    }

    Optional<User> userOutput = userRepository.findByEmail(updatepassword.getEmail());
    if(userOutput.isEmpty()){
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Optional: Email Not Found"));
    }

    userOutput.get().setPassword(encoder.encode(updatepassword.getPassword()));

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(updatepassword.getEmail(), updatepassword.getPassword()));
    String jwt = jwtUtils.generateJwtToken(authentication);


    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(
            jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  }

  @PostMapping("validateOTP")
  public ResponseEntity<?> validateOTP(@Valid @RequestBody updatePassword updatepassword) {
    UserDetails user = userDetailsService.loadUserByUsername(updatepassword.getEmail());

    if(ObjectUtils.isEmpty(user)) {
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Email Not Found"));
    }

    if(encoder.matches(updatepassword.getPassword(), user.getPassword())){
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "The new password must be different from the old password"));
    }

    Optional<User> userOutput = userRepository.findByEmail(updatepassword.getEmail());
    if(userOutput.isEmpty()){
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Optional: Email Not Found"));
    }

    userOutput.get().setPassword(encoder.encode(updatepassword.getPassword()));

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(updatepassword.getEmail(), updatepassword.getPassword()));
    String jwt = jwtUtils.generateJwtToken(authentication);


    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(
            jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  }
}
