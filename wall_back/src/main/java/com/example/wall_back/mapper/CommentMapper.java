package com.example.wall_back.mapper;

import com.example.wall_back.entity.Comment;
import com.example.wall_back.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    void up_comment(@RequestParam String id, @RequestParam String text,
                 @RequestParam int content_type, @RequestParam String media_url,
                    @RequestParam String post_id,
                 @RequestParam String ownername, @RequestParam String post_time);

    void comment_delete(@RequestParam String comment_id);

    List<Comment> get_comments(@RequestParam int limit, @RequestParam  int size, @RequestParam String post_id);

    List<Comment> my_comments(@RequestParam int limit, @RequestParam  int size, @RequestParam String username);
}
