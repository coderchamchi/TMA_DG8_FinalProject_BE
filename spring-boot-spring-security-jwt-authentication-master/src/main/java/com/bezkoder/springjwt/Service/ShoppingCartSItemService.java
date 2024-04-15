package com.bezkoder.springjwt.Service;


import com.bezkoder.springjwt.dto.ShoppingCartItemDTO;
import com.bezkoder.springjwt.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public interface ShoppingCartSItemService {

    boolean addItemToCart(User user, ShoppingCartItemDTO shoppingCartItemDTO);

    ShoppingCartItem updateItemInCart(Size size, int quantity, User user);

    boolean deleteItemFromCart(Product product, User user);
}
