package com.bezkoder.springjwt.Service.Impl;


import com.bezkoder.springjwt.Service.ShoppingCartService;
import com.bezkoder.springjwt.Service.SizeService;
import com.bezkoder.springjwt.Service.UserService;
import com.bezkoder.springjwt.dto.ItemUpdate;
import com.bezkoder.springjwt.dto.ProductListDTO;
import com.bezkoder.springjwt.dto.ShoppingCartItemDTO;
import com.bezkoder.springjwt.dto.SizeInCartDTO;
import com.bezkoder.springjwt.entities.*;
import com.bezkoder.springjwt.repository.ShoppingCartItemRepository;
import com.bezkoder.springjwt.repository.ShoppingCartRepository;
import com.bezkoder.springjwt.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;


    @Override
    public boolean saveShoppingCart(User user) {
        if (ObjectUtils.isEmpty(user)) {
            return false;
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCreateDate(LocalDate.now());
        shoppingCart.setUser(user);
        shoppingCart.setStatus(1);
        shoppingCartRepository.save(shoppingCart);
        return true;
    }

    @Override
    public List<ProductListDTO> getAllItem(long user) {
        ShoppingCart cart = shoppingCartRepository.getShoppingCartByUser((user));
        List<ProductListDTO> listProduct = shoppingCartItemRepository.gelAllItem(cart.getIdShoppingCart()).stream().map(
                item -> {
                    Optional<Size> size = sizeRepository.findById(item.getSize().getIdSize());
                    if (size.isPresent()) {
                        Size sizeDTO = size.get();
                        Size tempSize = new Size();
                        tempSize.setIdSize(sizeDTO.getIdSize());
                        tempSize.setProduct(sizeDTO.getProduct());
                        tempSize.setPrice(sizeDTO.getPrice());

                        ProductListDTO productListDTO = new ProductListDTO();
                        productListDTO.setProductName(String.valueOf(tempSize.getProduct().getProductName()));
                        productListDTO.setPrice(tempSize.getPrice());
                        productListDTO.setBase64(tempSize.getProduct().getBase64());
                        productListDTO.setIdProduct(tempSize.getProduct().getIdProduct());
                        return productListDTO;
                    }
                    return null;
                }
        ).collect(Collectors.toList());
        return listProduct;
    }

    @Override
    public boolean updateItemInShoppingCart(long id, ItemUpdate itemUpdate) {
        ShoppingCartItem item = shoppingCartItemRepository.getReferenceById(id);
        if (ObjectUtils.isEmpty(item)) {
            return false;
        }
        item.setSize(itemUpdate.getSize());
        item.setQuantity(itemUpdate.getQuantity());
        shoppingCartItemRepository.save(item);
        return true;
    }

    @Override
    public boolean addItemToCart(ShoppingCartItemDTO shoppingCartItemDTO) {
        if (ObjectUtils.isEmpty(shoppingCartItemDTO)) {
            return false;
        }
        User user = userService.findUserByUserName();
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(user.getUserId());
        if (shoppingCart.isEmpty()) {
            return false;
        }
        Optional<Size> size = sizeService.findByIdSize(shoppingCartItemDTO.getSize());
        if (size.isEmpty()) {
            return false;
        }
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setShoppingCart(shoppingCart.get());
        shoppingCartItem.setSize(size.get());
        shoppingCartItem.setQuantity(shoppingCartItemDTO.getQuantity());
        shoppingCartItemRepository.save(shoppingCartItem);
        return true;
    }

    @Override
    public ShoppingCart updateShoppingCart(long id, Map<String, Object> fields) {
        Optional<ShoppingCart> existingCart = shoppingCartRepository.findById(id);

        if(existingCart.isPresent()){
            fields.forEach((key, value)->
            {
                Field field = ReflectionUtils.findField(Product.class, key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingCart.get(), value);
            });
            return shoppingCartRepository.save(existingCart.get());
        }
        return null;
    }


}
