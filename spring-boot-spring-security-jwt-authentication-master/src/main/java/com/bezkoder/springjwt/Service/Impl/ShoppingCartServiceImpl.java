package com.bezkoder.springjwt.Service.Impl;



import com.bezkoder.springjwt.Service.ShoppingCartService;
import com.bezkoder.springjwt.dto.ShoppingCartDTO;
import com.bezkoder.springjwt.entities.ShoppingCart;
import com.bezkoder.springjwt.entities.User;
import com.bezkoder.springjwt.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Override
    public boolean saveShoppingCart(User user) {
        if(ObjectUtils.isEmpty(user)) {
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
    public boolean updateShoppingCart() {
        return false;
    }


}
