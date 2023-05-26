package com.example.wall_back.controller;

import com.example.wall_back.repository.UserRepository;
import com.example.wall_back.utils.JsonResult;
import com.example.wall_back.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Map>  register(@RequestBody User user_in) {
        Map<String, Object> map = new HashMap<>(3);

        String userName = user_in.getUsername();
        String password = user_in.getPassword();

        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String str = df.format(date);
        String userId = 'u' + str + userName;

        map.put("username", userName);
        try {
            userRepository.register(userId, userName, password);
            map.put("userId",userId);
            return new JsonResult<>(0, "", map);
        } catch (Exception e) {
            return new JsonResult<>(-1, e.getMessage(), map);
        }
    }


    @RequestMapping(value ="/login",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Map> login(@RequestBody User user_in){
        Map<String, Object> map = new HashMap<>(3);
        String username = user_in.getUsername();
        String password = user_in.getPassword();
        try {
            User user = userRepository.login(username, password);
            map.put("userId",user.getUserId());
            if(user.getPassword().equals(password)) {
                if(user.isIsmanager())
                {
                    return new JsonResult<>(1,"",map);
                }
                return new JsonResult<>(0,"",map);
            }
            else {
                return new JsonResult<>(-1,"error password",map);
            }
        }catch (Exception e)
        {
            return new JsonResult<>(-1,e.getMessage(),map);
        }
    }

    @RequestMapping(value ="/modify",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Map> modify(@RequestParam String old_password,
                                  @RequestParam String new_password,
                                  @RequestParam String old_username,
                                  @RequestParam String new_username) {
        Map<String, Object> map = new HashMap<>(3);
        User user = userRepository.login(old_username, old_password);
        if (user.getPassword().equals(old_password)) {
            try {
                userRepository.modify(old_username,new_username,new_password);
                return new JsonResult<>(0, "", map);
            } catch (Exception e) {
                return new JsonResult<>(-1, e.getMessage(), map);
            }
        } else {
            return new JsonResult<>(-1, "eooro old-password", map);
        }
    }
}
