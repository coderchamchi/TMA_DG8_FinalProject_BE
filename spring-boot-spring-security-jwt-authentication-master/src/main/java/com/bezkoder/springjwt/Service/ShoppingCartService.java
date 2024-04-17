package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.dto.ItemUpdate;
import com.bezkoder.springjwt.dto.ProductListDTO;
import com.bezkoder.springjwt.dto.ShoppingCartItemDTO;
import com.bezkoder.springjwt.dto.SizeInCartDTO;
import com.bezkoder.springjwt.entities.ShoppingCart;
import com.bezkoder.springjwt.entities.Size;
import com.bezkoder.springjwt.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface ShoppingCartService {

    // create a shoppingCart
    boolean saveShoppingCart(User use);

    // get all item in shoppingcart
    List<ProductListDTO> getAllItem(long user);

    // update a shoppingCart
    boolean updateItemInShoppingCart(long id, ItemUpdate itemUpdate);

    // add item to cart
    boolean addItemToCart(ShoppingCartItemDTO shoppingCartItemDTO);

    // update by patch
    ShoppingCart updateShoppingCart(long id, Map<String, Object> fields);


}
