package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.dto.itemDTO;
import com.bezkoder.springjwt.entities.Product;
import com.bezkoder.springjwt.entities.ShoppingCartItem;
import com.bezkoder.springjwt.entities.User;

import java.util.List;
import java.util.Set;

public interface ShoppingCartSItemervice {
    List<ShoppingCartItem> getallCart(Long id);

    boolean Shoppingcartaftersignup(User user);
    boolean addItemToCart(itemDTO itemDTO, User user);

    ShoppingCartItem updateItemInCart(Product product, int quantity, User customer);

//    boolean deleteItemFromCart(Product product, User user);

    ShoppingCartItem findCartItem(Set<Product> cartItems, Long productId);

}
