package com.example.wall_back.repository.Impl;

import com.example.wall_back.entity.Post;
import com.example.wall_back.mapper.PostMapper;
import com.example.wall_back.repository.PostRepository;
import com.example.wall_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {
    @Autowired
    PostMapper postMapper;
    @Override
    public void up_post(String id,String title, String text, int content_type, String media_url,
                        double location_x, double location_y, String ownername) {
        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String post_time = df.format(date);
        postMapper.up_post(id, title, text, content_type, media_url, location_x, location_y, ownername,post_time);
    }

    @Override
    public List<Post> get_posts(int page_num, int page_size, double location_x, double location_y, double distance) {
        int limit = (page_num-1)*page_size;
        return postMapper.get_posts(limit,page_size,location_x,location_y,distance);
    }


    @Override
    public List<Post> my_posts(int page_num, int page_size, String username) {
        int limit = (page_num-1)*page_size;
        return postMapper.my_posts(limit,page_size,username);
    }

    @Override
    public List<Post> update_posts(String post_id, String title, String text) {
        postMapper.update_posts(post_id,title,text);
        return postMapper.update_posts_get(post_id);
    }

    @Override
    public Post post_detail(String post_id) {
        return postMapper.post_detail(post_id);
    }
    @Override
    public void post_delete(String post_id){
        postMapper.post_delete(post_id);
    }

    @Override
    public List<Post> check_posts_list(int page_num, int page_size, int checked) {
        int limit = (page_num-1)*page_size;
        return postMapper.check_posts_list(limit,page_size,checked);
    }

    @Override
    public void check_post(String postId) {
        postMapper.post_check(postId);
    }

}
