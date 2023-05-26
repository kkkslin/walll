package com.example.wall_back.repository;
import com.example.wall_back.entity.Post;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostRepository {
    public void up_post(String id, String title,String text, int content_type, String media_url,
                        double location_x, double location_y, String ownername);

    public List<Post> get_posts(int page_num, int page_size, double location_x, double location_y, double distance);

    public List<Post> my_posts(int page_num, int page_size, String username);

    public List<Post> update_posts(String post_id,String title,String text);

    public Post post_detail(String post_id);

    public void post_delete(String post_id);


    public List<Post> check_posts_list(int page_num, int page_size, int checked);

    void check_post(String postId);
}
