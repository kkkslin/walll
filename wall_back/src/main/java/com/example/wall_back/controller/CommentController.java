package com.example.wall_back.controller;

import com.example.wall_back.entity.Comment;
import com.example.wall_back.entity.Post;
import com.example.wall_back.repository.CommentRepository;
import com.example.wall_back.utils.JsonResult;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value="/api/comment")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Map> up_comment( @RequestPart(required = false) MultipartFile file,
                                       @RequestPart Comment comment)
    {
        String text = comment.getText();
        String ownername = comment.getOwnername();
        if(ownername==null) ownername="anonymous users";
        if(text==null||text.length()==0) text = "no text";

        Map<String, Object> map = new HashMap<>(3);

        //post  id
        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date_str = df.format(date);
        String id = ownername+'_'+date_str;

        String media_url = "";
        if(comment.getContent_type()==1){
            if(file==null)
                return new JsonResult<>(-1,"file not existing", map);
            try {
                String originalFileName = file.getOriginalFilename().replaceAll(",|&|=", "");
                String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
                String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

                date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                date_str = sdf.format(date);
                String fileName =date_str +name + "." + type;

                String imgFilePath = "D:\\wall\\upload";
                File targetFile = new File(imgFilePath, fileName);
                if(!targetFile.getParentFile().exists()){ //注意，判断父级路径是否存在
                    targetFile.getParentFile().mkdirs();
                }

                file.transferTo(targetFile);

                media_url = "http://192.168.0.117:9001/"+fileName;
            } catch (Exception e) {
                return new JsonResult<>(-1,e.getMessage(), map);
            }
        }

        try {
            commentRepository.up_comment(id, text, comment.getContent_type(), media_url,
                                         comment.getPost_id(), ownername);
            map.put("id",id);
            map.put("content_type",comment.getContent_type());
            map.put("text",text);
            map.put("media_url",media_url);
            map.put("ownername",ownername);
            return new JsonResult<>(0, "", map);
        }catch (Exception e2)
        {
            return new JsonResult<>(-1,e2.getMessage(), map);
        }
    }


    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Map> post_delete( @RequestParam("comment_id") String comment_id )
    {
        Map<String, Object> map = new HashMap<>(3);
        try {
            commentRepository.comment_delete(comment_id);
            return new JsonResult<>(0,"",map);
        }catch (Exception e)
        {
            return new JsonResult<>(-1,e.getMessage(),map);
        }
    }

    @RequestMapping(method = RequestMethod.GET,params = {"page_num","page_size","post_id"})
    @ResponseBody
    public JsonResult<Map> get_comments( @RequestParam("page_num")  int page_num,
                                      @RequestParam("page_size") int page_size,
                                      @RequestParam("post_id") String post_id){
        Map<String, Object> map = new HashMap<>(3);
        if(post_id==null||post_id.length()==0)
            return new JsonResult<>(-1,"can not find post_id",map);

        try {
            List<Comment> comment = commentRepository.get_comments(page_num,page_size,post_id);
            map.put("comments",comment);
            return new JsonResult<>(0,"",map);
        }catch (Exception e)
        {
            return new JsonResult<>(-1,e.getMessage(),map);
        }
    }

    @RequestMapping(value = "/mycomment",method = RequestMethod.GET,params = {"page_num","page_size","username"})
    @ResponseBody
    public JsonResult<Map> my_comments( @RequestParam("page_num")  int page_num,
                                         @RequestParam("page_size") int page_size,
                                         @RequestParam("username") String username){
        System.out.println(username);
        Map<String, Object> map = new HashMap<>(3);
        if(username==null||username.length()==0)
            return new JsonResult<>(-1,"unknown username",map);

        try {
            List<Comment> comment = commentRepository.my_comments(page_num,page_size,username);
            map.put("comments",comment);
            return new JsonResult<>(0,"",map);
        }catch (Exception e)
        {
            return new JsonResult<>(-1,e.getMessage(),map);
        }
    }
}
