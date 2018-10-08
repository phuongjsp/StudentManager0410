package edu.zera.student.manager.dao;

import edu.zera.student.manager.model.User;

import java.util.List;

public interface UserDAO {
    User save(User user);
    boolean update(User user);
    boolean delete(String username);
    User findByUsername(String username);
    List<User> findAll();
}
