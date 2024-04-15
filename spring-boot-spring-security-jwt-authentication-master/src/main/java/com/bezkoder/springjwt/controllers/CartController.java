package com.bezkoder.springjwt.controllers;



import com.bezkoder.springjwt.Service.ShoppingCartSItemService;
import com.bezkoder.springjwt.Service.ShoppingCartService;
import com.bezkoder.springjwt.Service.UserService;


import com.bezkoder.springjwt.dto.*;


import com.bezkoder.springjwt.entities.Product;
import com.bezkoder.springjwt.entities.Size;
import com.bezkoder.springjwt.entities.User;
import com.bezkoder.springjwt.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("ProjectSJ/shoppingCartItem")
public class CartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartSItemService shoppingCartSItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @GetMapping("/all")
    public ResponseEntity<List<ProductListDTO>> getAllItemInCart() {
        User user = userService.findUserByUserName();
        List<ProductListDTO> listCart = shoppingCartService.getAllItem((user.getUserId()));
        return new ResponseEntity<List<ProductListDTO>>(listCart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseJson<Boolean>> addItemToCart(@Valid @RequestBody ShoppingCartItemDTO itemDTO) {
        if (itemDTO == null) {
            return ResponseEntity.badRequest().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Not Found Item and Quantity"));
        } else if (ObjectUtils.isEmpty(itemDTO)){
            return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Not Found Item and Quantity"));
        }
        try {
            User user = userService.findUserByUserName();
            if (ObjectUtils.isEmpty(user)){
                return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "User Not Found"));
            }
            boolean check = shoppingCartService.addItemToCart(itemDTO);
                if (!check)
                {
                    return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Size Or Quantity is null"));
                }
                return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, "Added a Item into cart for user: "+ user.getEmail()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseJson<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi máy chủ nội bộ"));
        }

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseJson<Boolean>> updateItem(@Validated @PathVariable("id") Long id, @RequestBody ItemUpdate itemDTO, BindingResult bindingResult) {
        if (ObjectUtils.isEmpty(itemDTO)){
            return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "Not Found Item and Quantity"));
        }
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }

            ResponseJson<Boolean> errorResponse = new ResponseJson<>(Boolean.FALSE, HttpStatus.BAD_REQUEST, errors.toString());
            return ResponseEntity.badRequest().body(errorResponse);
        }
        try {
            User user = userService.findUserByUserName();
            if (ObjectUtils.isEmpty(user)){
                return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "User Not Found"));
            }
            boolean check = shoppingCartService.updateItemInShoppingCart(id, itemDTO);
            if (!check)
            {
                return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "User Not Found"));
            }
            return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, "Updated a item for user: "+ user.getEmail()));
        }
        catch (Exception e){
            return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "User Not Found"));
        }
    }

//    @DeleteMapping("/deleteproduct")
//    public ResponseEntity<ResponseJson<Boolean>> deleteproduct(@RequestBody  Product product){
//        try {
//            User user =userService.findUserByUserName();
//            if (ObjectUtils.isEmpty(user)){
//                return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "User Not Found"));
//            }
//            boolean check = shoppingCartService.deleteItemFromCart(product, user);
//            if (!check){
//                return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "User Not Found"));
//            }
//            return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.ACCEPTED, "Deleted!"));
//        }
//        catch (Exception e){
//            return ResponseEntity.ok().body(new ResponseJson<>(Boolean.FALSE, HttpStatus.NOT_FOUND, "User Not Found"));
//        }
//    }

}
