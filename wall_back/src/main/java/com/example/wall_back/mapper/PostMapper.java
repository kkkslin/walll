package com.example.wall_back.mapper;

import com.example.wall_back.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Mapper
public interface PostMapper {
    void up_post(@RequestParam String id, @RequestParam String title, @RequestParam String text,
                 @RequestParam int content_type, @RequestParam String media_url,
                 @RequestParam double location_x, @RequestParam  double location_y,
                 @RequestParam String ownername, @RequestParam String post_time);

    Post post_detail(@RequestParam String post_id);

    void post_delete(@RequestParam String post_id);

    List<Post> get_posts(@RequestParam int limit,@RequestParam  int size,
                         @RequestParam double location_x,@RequestParam  double location_y,
                         @RequestParam double distance);

    List<Post> my_posts(@RequestParam int limit, @RequestParam  int size,@RequestParam String username);

    List<Post> update_posts(@RequestParam String post_id, @RequestParam String title,@RequestParam String text);

    List<Post> update_posts_get(@RequestParam String post_id);

    List<Post> check_posts_list(@RequestParam int limit,@RequestParam int size,@RequestParam int checked);

    void post_check(@RequestParam String post_id);
}
/*void register(@RequestParam String userId,@RequestParam String userName,@RequestParam String password);*/