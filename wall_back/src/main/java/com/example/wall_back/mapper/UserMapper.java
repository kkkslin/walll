package com.example.wall_back.mapper;

import com.example.wall_back.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Mapper
public interface UserMapper {
    void register(@RequestParam String userId,@RequestParam String userName,@RequestParam String password);
    User login(@RequestParam String username,@RequestParam String password);
    // Boolean update(String userId,Boolean status);

    void modify(@RequestParam String username,@RequestParam String new_password);
}

