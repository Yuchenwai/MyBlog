package com.zhoufeng.myblog.service;

import com.zhoufeng.myblog.entity.User;

import java.util.List;


public interface UserService {

    List<User> list();

    Integer remove(Integer id);

    Integer modify(User user);

    Integer register(User user);

    User getByUsername(String username);

    User getByEmail(String email);
}
