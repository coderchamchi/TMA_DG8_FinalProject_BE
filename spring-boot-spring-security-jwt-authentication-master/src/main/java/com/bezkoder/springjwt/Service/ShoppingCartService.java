package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.dto.ShoppingCartDTO;
import com.bezkoder.springjwt.entities.ShoppingCart;
import com.bezkoder.springjwt.entities.User;
import org.springframework.stereotype.Service;


public interface ShoppingCartService {

    // create a shoppingCart
    boolean saveShoppingCart(User use);

    // update a shoppingCart
    boolean updateShoppingCart();
}
