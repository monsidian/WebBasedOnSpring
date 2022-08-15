package com.lyt.community.controller;

import com.lyt.community.entity.DiscussPost;
import com.lyt.community.entity.Page;
import com.lyt.community.entity.User;
import com.lyt.community.service.DiscussPostService;
import com.lyt.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

        @Autowired
        // 将service注入进来以便调用
        private DiscussPostService discussPostService;

        @Autowired
        private UserService userService;

        // 定义访问路径为首页，GET方法
        @RequestMapping(path = "/index", method = RequestMethod.GET)
        // 显示论坛首页
        // 可以返回model and view 也可以直接返回String表示模板的路径
        public String getIndexPage(Model model, Page page){
                // 方法调用之前，springMVC会自动实例化Model和Page，并将Page注入到Model
                // 所以在thymeleaf中可以直接访问Page对象中的数据
                // 不需要model.addAttribute(page)
                page.setRows(discussPostService.findDiscussRows(0));
                page.setPath("/index");


                // 先查询前10条帖子，返回user_id的集合
                List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
                List<Map<String, Object>> discussPosts = new ArrayList<>();
                if(list != null){
                        for(DiscussPost post: list){
                                Map<String, Object> map = new HashMap<>();
                                map.put("post", post);
                                // 根据帖子找到user_id, 再根据user_id找到完整的user信息
                                User user = userService.findUserById(post.getUserId());
                                map.put("user", user);
                                discussPosts.add(map);
                        }
                }
                // 将需要展示的结果装入model中
                model.addAttribute("discussPosts", discussPosts);
                return "/index";
        }
}
