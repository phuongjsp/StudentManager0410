package edu.zera.student.manager.services.impl;

import edu.zera.student.manager.dao.UserDAO;
import edu.zera.student.manager.model.User;
import edu.zera.student.manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder ;
    @Override
    public boolean checkLogin(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user==null)return false;
        if (!user.isEnable())return false;
        return passwordEncoder.matches(password,user.getPassword());
    }

    @Override
    public String register(String username, String password) {
        if (userDAO.findByUsername(username)!=null) return "";
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnable(true);
        userDAO.save(user);
        return user.getUsername();
    }

    @Override
    public boolean enableUser(String username, boolean enable) {
        User user = userDAO.findByUsername(username);
        if (user==null) return false;
        user.setEnable(enable);
        userDAO.update(user);
        return true;
    }

    @Override
    public Map<String,Boolean> findAllUsername(String username) {
        //java <7
//        Map<String,Boolean> map = new HashMap<>();
//        userDAO.findAll().forEach(user -> {
//            map.put(user.getUsername(),user.isEnable());
//        });
//        return map;
        //        java 8
        if (!this.isAdmin(username))
            return new HashMap<>();
        return userDAO.findAll().stream()
                .collect(Collectors.toMap(User::getUsername,
                        User::isEnable, (a, b) -> b));

    }

    @Override
    public boolean isEnableUser(String username) {
        User user = userDAO.findByUsername(username);
        return user.isEnable();
    }

    @Override
    public boolean isAdmin(String username) {
        User user = userDAO.findByUsername(username);
        return user.isAdmin();
    }
}
