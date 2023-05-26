package com.example.wall_back.controller;

import com.example.wall_back.entity.Post;
import com.example.wall_back.repository.PostRepository;
import com.example.wall_back.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value="/api/post")
public class PostController {
    @Autowired
    private PostRepository postRepository;


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Map> up_post( @RequestPart(required = false) MultipartFile file,
                                    @RequestPart Post post )
    {
        String title = post.getTitle();
        if(title==null||title.length()==0) title = "no title";
        String text = post.getText();
        if(text==null||text.length()==0) text = "no text";
        int content_type = post.getContent_type();
        double location_x = post.getLocation_x();
        double location_y = post.getLocation_y();
        String ownername = post.getOwnername();
        if(ownername==null) ownername="anonymous";

        Map<String, Object> map = new HashMap<>(3);

        //post  id
        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date_str = df.format(date);
        String id = ownername+'_'+date_str;

        String media_url = "";
        if(content_type==1){
            if(file==null)
                return new JsonResult<>(-1,"file not existing", map);
            //System.out.println(file);
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
                media_url = "http://192.168.0.124:9001/"+fileName;
            } catch (Exception e) {
                return new JsonResult<>(-1,e.getMessage(), map);
            }
        }

        try {
            postRepository.up_post(id, title, text, content_type, media_url, location_x, location_y, ownername);
            map.put("id",id);
            map.put("title",title);
            map.put("content_type",content_type);
            map.put("text",text);
            map.put("media_url",media_url);
            map.put("location_x",location_x);
            map.put("location_y",location_y);
            return new JsonResult<>(0, "", map);
        }catch (Exception e2)
        {
            return new JsonResult<>(-1,e2.getMessage(), map);
        }

    }

    @RequestMapping(method = RequestMethod.POST,consumes="application/json")
    @ResponseBody
    public JsonResult<Map> up_post2( @RequestBody Post post )
    {
        Map<String, Object> map = new HashMap<>(3);
        String title = post.getTitle();
        if(title==null||title.length()==0) title = "no title";
        String text = post.getText();
        if(text==null||text.length()==0) text = "no text";
        int content_type = post.getContent_type();
        double location_x = post.getLocation_x();
        double location_y = post.getLocation_y();
        String ownername = post.getOwnername();
        if(ownername==null) ownername="anonymous users";
        String media_url = "";
        //post  id
        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String date_str = df.format(date);
        String id = ownername+'_'+date_str;
        try {
            postRepository.up_post(id, title, text, content_type, media_url, location_x, location_y, ownername);
            map.put("id",id);
            map.put("title",title);
            map.put("content_type",content_type);
            map.put("text",text);
            map.put("media_url",media_url);
            map.put("location_x",location_x);
            map.put("location_y",location_y);
            return new JsonResult<>(0, "", map);
        }catch (Exception e2)
        {
            return new JsonResult<>(-1,e2.getMessage(), map);
        }
    }

    @RequestMapping(method = RequestMethod.GET, params = {"post_id"})
    @ResponseBody
    public JsonResult<Map> post_detail( @RequestParam("post_id") String post_id )
    {
        Map<String, Object> map = new HashMap<>(3);
        try {
            Post post = postRepository.post_detail(post_id);
            map.put("id", post.getId());
            map.put("title", post.getTitle());
            map.put("content_type",post.getContent_type());
            map.put("text",post.getText());
            map.put("media_url",post.getMedia_url());
            map.put("location_x",post.getLocation_x());
            map.put("location_y",post.getLocation_y());
            return new JsonResult<>(0,"",map);
        }catch (Exception e)
        {
            return new JsonResult<>(-1,e.getMessage(),map);
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Map> post_delete( @RequestParam("post_id") String post_id )
    {
        Map<String, Object> map = new HashMap<>(3);
        try {
            postRepository.post_delete(post_id);
            return new JsonResult<>(0,"",map);
        }catch (Exception e)
        {
            return new JsonResult<>(-1,e.getMessage(),map);
        }
    }

    @RequestMapping(method = RequestMethod.GET,params = {"page_num","page_size"})
    @ResponseBody
    public JsonResult<Map> get_posts( @RequestParam("page_num")  int page_num,
                                      @RequestParam("page_size") int page_size,
                                      @RequestParam(value = "location_x",required = false) double location_x,
                                      @RequestParam(value = "location_y",required = false) double location_y,
                                      @RequestParam(value = "distance",required = false) double distance){
        distance = distance>0? distance:50;
        Map<String, Object> map = new HashMap<>(3);
        try {
            List<Post> post = postRepository.get_posts(page_num,page_size,location_x,location_y,distance);
            map.put("posts",post);
            return new JsonResult<>(0,"",map);
        }catch (Exception e)
        {
            return new JsonResult<>(-1,e.getMessage(),map);
        }
    }


    @RequestMapping(value = "/mypost",method = RequestMethod.GET,params = {"page_num","page_size","username"})
    @ResponseBody
    public JsonResult<Map> my_posts( @RequestParam("page_num")  int page_num,
                                      @RequestParam("page_size") int page_size,
                                      @RequestParam("username") String username){
        Map<String, Object> map = new HashMap<>(3);
        if(username==null||username.length()==0)
            return new JsonResult<>(-1,"unknown username",map);
        try {
            List<Post> post = postRepository.my_posts(page_num,page_size,username);
            map.put("posts",post);
            return new JsonResult<>(0,"",map);
        }catch (Exception e)
        {
            return new JsonResult<>(-1,e.getMessage(),map);
        }

    }

    @RequestMapping(method = RequestMethod.PUT,params = {"post_id"})
    @ResponseBody
    public JsonResult<Map> update_post( @RequestPart(required = false) MultipartFile file,
                                        @RequestParam String post_id,
                                        @RequestPart(required = false) Post post )
    {
        String title = post.getTitle();
        if(title==null||title.length()==0) title = "no title";
        String text = post.getText();
        if(text==null||text.length()==0) text = "no text";
        Map<String, Object> map = new HashMap<>(3);
        try{
            List<Post> post_result = postRepository.update_posts(post_id,title,text);
            map.put("posts",post_result);
            return new JsonResult<>(0,"",map);
        }catch (Exception e)
        {
            return new JsonResult<>(-1,e.getMessage(),map);
        }
    }

    @RequestMapping(value = "/check",method = RequestMethod.GET,params = {"page_num","page_size","checked"})
    @ResponseBody
    public JsonResult<Map> check_posts_list( @RequestParam("page_num")  int page_num,
                                             @RequestParam("page_size") int page_size,
                                             @RequestParam("checked") int checked){
        Map<String, Object> map = new HashMap<>(3);
        try {
            List<Post> post = postRepository.check_posts_list(page_num,page_size,checked);
            map.put("posts",post);
            return new JsonResult<>(0,"",map);
        }catch (Exception e)
        {
            return new JsonResult<>(-1,e.getMessage(),map);
        }
    }

    @RequestMapping(value = "/check",method = RequestMethod.POST,params = {"post_id"})
    @ResponseBody
    public JsonResult<Map> check_post( @RequestParam("post_id") String post_id){
        Map<String, Object> map = new HashMap<>(3);
        try {
            postRepository.check_post(post_id);
            return new JsonResult<>(0,"",map);
        }catch (Exception e)
        {
            return new JsonResult<>(-1,e.getMessage(),map);
        }
    }
}
