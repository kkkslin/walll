package com.example.wall_back.repository.Impl;

import com.example.wall_back.entity.Comment;
import com.example.wall_back.entity.Post;
import com.example.wall_back.mapper.CommentMapper;
import com.example.wall_back.mapper.PostMapper;
import com.example.wall_back.repository.CommentRepository;
import com.example.wall_back.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public void up_comment(String id, String text, int content_type, String media_url,
                           String post_id, String ownername) {
        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String post_time = df.format(date);
        commentMapper.up_comment(id, text, content_type, media_url, post_id, ownername,post_time);
    }

    @Override
    public void comment_delete(String comment_id){
        commentMapper.comment_delete(comment_id);
    }

    @Override
    public List<Comment> get_comments(int page_num, int page_size, String post_id) {
        int limit = (page_num-1)*page_size;
        return commentMapper.get_comments(limit, page_size, post_id);
    }

    @Override
    public List<Comment> my_comments(int page_num, int page_size, String username) {
        int limit = (page_num-1)*page_size;
        return commentMapper.my_comments(limit, page_size, username);
    }
}
