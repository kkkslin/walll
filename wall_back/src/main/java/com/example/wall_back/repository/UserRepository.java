package com.example.wall_back.repository;

import com.example.wall_back.entity.User;

import java.util.List;

public interface UserRepository {
    public void register(String userId,String userName,String password);
    public User login(String username,String password);
    // public Boolean update(String userId,Boolean status);
    public void modify(String old_username,String new_username,String new_password);
}

