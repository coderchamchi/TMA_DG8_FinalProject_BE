package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.PaymentService;
import com.bezkoder.springjwt.Service.ShoppingCartService;
import com.bezkoder.springjwt.Service.UserService;
import com.bezkoder.springjwt.dto.PaymentDTO;
import com.bezkoder.springjwt.dto.ResponseJson;
import com.bezkoder.springjwt.entities.Payment;
import com.bezkoder.springjwt.entities.PaymentResponse;
import com.bezkoder.springjwt.entities.ShoppingCart;
import com.bezkoder.springjwt.entities.User;
import com.bezkoder.springjwt.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("ProjectSJ/Payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @GetMapping("/user")
    public  ResponseEntity<ResponseJson<PaymentResponse>> getPayment(){
        User user = userService.findUserByUserName();
        ShoppingCart cart = shoppingCartRepository.getShoppingCartByUser(user.getUserId());
        if(cart == null){
            return ResponseEntity.badRequest().body(new ResponseJson<>("Cart isn't found" ));
        }
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(cart.getIdShoppingCart());
        if (shoppingCart.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseJson<>("Cart isn't found" ));
        }
        PaymentResponse paymentResponse =  paymentService.findById(shoppingCart.get().getIdShoppingCart());
        if(ObjectUtils.isEmpty(paymentResponse)){
            return ResponseEntity.ok().body(new ResponseJson<>("Payment isn't found" ));
        }
        return ResponseEntity.ok().body(new ResponseJson<>(paymentResponse, HttpStatus.ACCEPTED, "Success"));
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseJson<Boolean>> savePayment(@RequestBody PaymentDTO paymentDTO){
        User user = userService.findUserByUserName();
        if (ObjectUtils.isEmpty(user)){
            return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "User isn't Found"));
        }
        boolean check = paymentService.savePayment(user.getUserId(), paymentDTO);
        if (!check){
            return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "PaymentMethod or Voucher isn't Found"));
        }
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.ACCEPTED, "Success"));
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Payment> updateproductbypatch(@PathVariable long id, @RequestBody Map<String,Object> fields){
        Payment payment= paymentService.updatePayment(id, fields);
        if(payment != null){
            return new ResponseEntity<>(payment, HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
