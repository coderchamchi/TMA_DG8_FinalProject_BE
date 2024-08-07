package com.bezkoder.springjwt.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


import com.bezkoder.springjwt.Service.Impl.UserDetailsServiceImpl;
import com.bezkoder.springjwt.Service.RoleService;
import com.bezkoder.springjwt.Service.ShoppingCartService;
import com.bezkoder.springjwt.Service.UserService;

import com.bezkoder.springjwt.config.jwt.AuthEntryPointJwt;
import com.bezkoder.springjwt.dto.ResponseJson;
import com.bezkoder.springjwt.dto.UserDTO;
import com.bezkoder.springjwt.dto.updatePassword;
import com.bezkoder.springjwt.entities.Product;
import com.bezkoder.springjwt.exception.ErrorResponse;
import com.bezkoder.springjwt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

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
//        Tại sao lại chọn kiểu trả về là <>/ Kiểu Generic Data
//        --> Vì sau này làm việc với FE, làm ở Swagger, chọn kiểu Generic để Swagger tự động biết phần example value
//        luôn, FE dựa vào đó code và kết hợp FE với BE thuận lợi hơn
{
  if (loginRequest.getEmail() == null){
    return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Email is Null"));
  }
  if (loginRequest.getPassword() == null){
    return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Password is Null"));
  }
  try {
    // Lấy thông tin người dùng từ cơ sở dữ liệu
    UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());
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

    return ResponseEntity.ok(new ResponseJson<>(new JwtResponse(
            jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles), "login success"));
  }
  catch (UsernameNotFoundException e) {
    return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "User Not Found, Email Wrong!"));
  }
  catch (AuthenticationException e) {
    return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "User Not Found"));
  }
}
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {


    if (signUpRequest.getEmail() == null){
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Email is Null"));
    }
    if (signUpRequest.getUsername() == null){
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Username is Null"));
    }
    if (signUpRequest.getBirthday() == null){
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Birthday is Null"));
    }
    if (signUpRequest.getPassword() == null){
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Password is Null"));
    }

    if (userService.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Username already exists"));
    }

    if (userService.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "Email already exists"));
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

    return ResponseEntity
            .ok()
            .body(new ResponseJson<>(Boolean.TRUE, HttpStatus.CREATED, "Signup Successfully"));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {

    Map<String, String> errors = new HashMap<>();

    e.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();

      // Kiểm tra trường lỗi và thiết lập thông báo cụ thể
      errors.put(fieldName, getValidationErrorMessage(fieldName, errorMessage));
    });

    // Trả về ResponseJson với danh sách lỗi
    return ResponseEntity.badRequest().body(new ResponseJson<>((Object) errors, HttpStatus.BAD_REQUEST, String.valueOf(Boolean.FALSE)));
  }

  // Hàm phụ để lấy thông báo lỗi chi tiết
  private String getValidationErrorMessage(String fieldName, String defaultMessage) {
    if (fieldName.equals("email") && defaultMessage.contains("NotBlank")) {
      return "Email is Null!";
    } else if (fieldName.equals("password") && defaultMessage.contains("NotBlank")) {
      return "Password is Null!";
    } else {
      // Giữ nguyên message mặc định cho các lỗi khác
      return defaultMessage;
    }
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

  @PatchMapping("/update/patch/{id}")
  public ResponseEntity<?> updateUserByPatch
          (@PathVariable("id") Long id, @RequestBody Map<String,Object> fields )
  {
    User user= userService.updatebypatch(id, fields);
    if(user != null){
      return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
    else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<List<User>> getAllUser(){
      List<User> listUser = userService.getalluser();
    return new ResponseEntity<List<User>>(listUser, HttpStatus.OK);
  }

  @GetMapping("/user")
  public ResponseEntity<Object> getUser(){
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(principal.equals("anonymousUser")){
      logger.error("User haven't SignIn");
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "User Haven't SignIn"));
    }
    return new ResponseEntity<>(principal, HttpStatus.OK);
  }

  @GetMapping("/getAuth")
  public ResponseEntity<Object> getAuthenticationManager() {
    Object principal = SecurityContextHolder.getContext().getAuthentication();
    if(ObjectUtils.isEmpty(principal)){
      return new ResponseEntity<>((Object) "Null", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(principal, HttpStatus.OK);
  }

  @GetMapping("/userinfo")//thong tin chi tiet, cac field co trong entity user
  public ResponseEntity<User> getInfoUser(){
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
    try {
      UserDetails user = userDetailsService.loadUserByUsername(updatepassword.getEmail());

      if (ObjectUtils.isEmpty(user)) {
        return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Email Not Found"));
      }

      if (encoder.matches(updatepassword.getPassword(), user.getPassword())) {
        return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "The new password must be different from the old password"));
      }

      Optional<User> userOutput = userRepository.findByEmail(updatepassword.getEmail());
      if (userOutput.isEmpty()) {
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

      return ResponseEntity.ok(new ResponseJson<>(new JwtResponse(
              jwt,
              userDetails.getId(),
              userDetails.getUsername(),
              userDetails.getEmail(),
              roles), "update password success"));
    }
    catch (UsernameNotFoundException e) {
      return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, "User Not Found, Email Wrong!"));
    }
  }

}
