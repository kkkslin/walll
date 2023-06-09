package com.example.wall_back.repository.Impl;

import com.example.wall_back.entity.User;
import com.example.wall_back.mapper.UserMapper;
import com.example.wall_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    UserMapper userMapper;

    @Override
    public void register(String userId, String userName,String password) {
       userMapper.register(userId, userName,password);
    }
    @Override
    public User login(String username, String password) {
        return userMapper.login(username,password);
    }

    @Override
    public void modify(String username,String new_password)
    {
        userMapper.modify(username, new_password);
    }
}

