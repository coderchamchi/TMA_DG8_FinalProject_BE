//package com.bezkoder.springjwt.Service.Impl;
//
//import com.bezkoder.springjwt.Service.ShoppingCartSItemService;
//import com.bezkoder.springjwt.dto.ShoppingCartItemDTO;
//import com.bezkoder.springjwt.entities.*;
//import com.bezkoder.springjwt.repository.ShoppingCartRepository;
//import com.bezkoder.springjwt.repository.SizeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.ObjectUtils;
//
//import java.util.Optional;
//
//public class ShoppingCartItemServiceImpl implements ShoppingCartSItemService {
//
//    @Autowired
//    ShoppingCartRepository shoppingCartRepository;
//
//    @Autowired
//    SizeRepository sizeRepository;
//
//    @Override
//    public boolean addItemToCart(User user, ShoppingCartItemDTO shoppingCartItemDTO) {
//        Optional<Size> size = sizeRepository.findById(shoppingCartItemDTO.getSize());
//        if(size.isPresent()){
//            if(ObjectUtils.isEmpty(user.getShoppingCart())){
//                return false;
//            }
//            ShoppingCart shoppingCart = shoppingCartRepository.shoppingCart(user);
//            if(ObjectUtils.isEmpty(shoppingCart) && ObjectUtils.isEmpty((size))) {
//                return false;
//            }
//            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
//            shoppingCartItem.setShoppingCart(shoppingCart);
//            shoppingCartItem.setQuantity(shoppingCartItemDTO.getQuantity());
//            shoppingCartItem.setSize(size.get());
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public ShoppingCartItem updateItemInCart(Size size, int quantity, User user) {
//        return null;
//    }
//
//    @Override
//    public boolean deleteItemFromCart(Product product, User user) {
//        return false;
//    }
//}
