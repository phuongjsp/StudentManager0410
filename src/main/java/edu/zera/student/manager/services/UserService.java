package edu.zera.student.manager.services;

import edu.zera.student.manager.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    boolean checkLogin(String username,String password);
    String register(String username,String password);
    boolean enableUser(String username,boolean enable);
    Map<String,Boolean> findAllUsername(String username);
    boolean isEnableUser(String username);
    boolean isAdmin(String username);
}
