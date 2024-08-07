package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.constant.WebUnit;
import com.bezkoder.springjwt.entities.Product;
import com.bezkoder.springjwt.entities.User;
import com.bezkoder.springjwt.Service.UserService;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    @Override
    public boolean existsByPassword(String password) {
        return userRepository.existsByPassword(password);
    }
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveOrupdate(User user) {
        return userRepository.save(user);
    }

//    @Override
//    public boolean updatePassword(updatePassword user) {
//        Optional<User> userCheck = userRepository.findByEmail(user.getEmail());
//        if(userCheck.isPresent()){
//            userCheck.get().setPassword(user.getPassword());
//            return true;
//        }
//        return false;
//    }

    @Override
    public User findUserByUserName() {
        return userRepository.findUserByUsername(WebUnit.getUsername());
    }

    @Override
    public List<User> getalluser(){
        return userRepository.findAll();
    }

    @Override
    public List<String> getAllUsername() {
        return userRepository.GetAllUsername();
    }

    @Override
    public List<String> getAllEmail() {
        return userRepository.GetAllEmail();
    }

    @Override
    public User updatebypatch(long id, Map<String, Object> fields) {
        Optional<User> existingUser = userRepository.findById(id);

        if(existingUser.isPresent()){
            fields.forEach((key, value)->
            {
                Field field = ReflectionUtils.findField(User.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingUser.get(), value);
            });
            return userRepository.save(existingUser.get());
        }
        return null;
    }
}

