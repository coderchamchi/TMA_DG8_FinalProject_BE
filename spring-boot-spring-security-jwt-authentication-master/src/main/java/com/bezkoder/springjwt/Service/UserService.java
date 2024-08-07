package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.dto.updatePassword;
import com.bezkoder.springjwt.entities.Product;
import com.bezkoder.springjwt.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    User saveOrupdate(User user);

//    boolean updatePassword(updatePassword user);

    boolean existsByPassword(String password);

    User findUserByUserName();

    List<User> getalluser();

    List<String> getAllUsername();

    List<String> getAllEmail();

    User updatebypatch(long id, Map<String, Object> fields);
}
