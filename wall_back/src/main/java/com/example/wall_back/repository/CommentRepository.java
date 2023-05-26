package com.example.wall_back.repository;
import com.example.wall_back.entity.Comment;
import com.example.wall_back.entity.Post;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public interface CommentRepository {
    public void up_comment(String id, String text, int content_type,
                           String media_url, String post_id, String ownername);
    public void comment_delete(String comment_id);

    public List<Comment> get_comments(int page_num, int page_size, String post_id);

    public List<Comment> my_comments(int page_num, int page_size, String username);
}
