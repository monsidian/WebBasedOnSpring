package com.lyt.community.controller;

import com.lyt.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller //处理请求，一般流程为controller处理浏览器的请求->调用service业务组件->调用DAO访问数据库
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired  //将AlphaService注入给AlphaController以便调用
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello Spring Boot.";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData(){    //调用alphaService实现模拟数据查询请求
        return alphaService.find();
    }

    // 底层处理GET请求的方式
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){     //通过response对象可以直接向浏览器返回任何数据，所以不依靠方法的返回值。输入为请求对象和响应对象
        // 获取请求数据
        // 请求的第一行数据
        System.out.println(request.getMethod());    // 获取请求方式
        System.out.println(request.getServletPath());   // 获取请求路径
        // 中间的消息头
        Enumeration<String> enumeration = request.getHeaderNames();   // 获取请求行的key并赋值给一个迭代器，因为请求行有很多数据，并封装为了key: value结构
        while(enumeration.hasMoreElements()){       // 是否还有其他元素
                String name = enumeration.nextElement();    // 获取当前元素
                String value = request.getHeader(name);     // 根据key获取value
                System.out.println(name + ":" + value);
            }
        // 请求体，包含了业务数据，即参数
        System.out.println(request.getParameter("code"));

        // 返回响应数据
        response.setContentType("text/html; charset=utf-8");   //设置返回对象的类型为网页类型的文本并支持中文，也可以为字符串，图片
        try (
                PrintWriter writer = response.getWriter();   //获取输出流，使response能够向浏览器输出网页
                ){
            writer.write("<h1>论坛</h1>");    //网页一级标题
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // 简化版处理GET请求并传参的方法
    // 方法1：路径 + “?” + 参数
    //GET请求，向服务器获取数据时使用，默认发送的请求

    //查询所有学生，当前为第一页，每页显示20条数据，路径为：/students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET) //强制只有GET请求才能访问到
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit){  // @RequestParam将请求对象中的参数绑定到控制器中方法的参数
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }
    // 方法2：将参数数值编排到路径当中，成为路径的一部分
    // 根据学号查询某一学生
    // /student/123
    // /student?id=123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){   //@PathVariable将请求路径中的参数绑定到控制器中方法的参数
        System.out.println(id);
        return "a student";
    }

    // POST请求处理方法
    // POST请求为浏览器向服务器提交数据，与GET请求相对，两种请求基本可以解决大部分功能
    // 浏览器要向服务器提交数据，首先要打开一个具有表单的网页，因此需要先创建一个静态网页，存放于static；templates存放的是动态网页的模板
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String savaStudent(String name, int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    // 响应html数据
    // 方法1，将model和view的数据都装入ModelAndView对象里
    @RequestMapping(path = "/teacher", method = RequestMethod.GET) // 不加@ResponseBody，默认返回html页面
    public ModelAndView getTeacher(){   // 返回model和视图两份数据，用于提交给模板引擎DispatcherServlet生成动态html
        ModelAndView mav = new ModelAndView();  //实例化一个对象
        mav.addObject("name", "张三");    //模板里需要多少个数据就add多少个
        mav.addObject("age", 30);
        mav.setViewName("/demo/view");    //模板的路径和名字，模板存放templates文件夹下，因此从templates下级目录开始;themleaf默认是html文件，因此view不用加html后缀，实则为view.html
        return mav;
    }

    // 方法2，将model数据装入参数中，再直接返回view视图
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){  //返回html网页时，String类型表示返回网页的路径,model写入输入中，由DispatcherServlet识别并自动实例化Model对象再传入方法
        model.addAttribute("name", "电子科技大学");
        model.addAttribute("age", 2022 - 1956);
        return "/demo/view";
    }

    // 响应JSON数据，异步请求下使用（即当前网页不刷新却访问了服务器，比如注册账号时，输入完账号后判断该账号是否被占用，即发生了一次异步请求）
    //Json是一种具有特定格式的字符串 Java对象 -> JSON字符串 -> JS对象
    // 服务器所使用的是JAVA对象，而浏览器处理的是JS对象，JSON可以衔接两者
    // 一个对象，直接返回
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody   // 因为返回的不是html而是JSON字符串，所需需要加上该注解
    public Map<String, Object> getEmp(){
        Map<String, Object> emp = new HashMap<>();  //Map<String, Object>类似于字典
        emp.put("name", "李四");
        emp.put("age", 23);
        emp.put("salary", 15000.00);
        return emp;
    }

    //多个对象，返回数组
    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody   // 因为返回的不是html而是JSON字符串，所需需要加上该注解
    public List<Map<String, Object>> getEmps(){
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 26);
        emp.put("salary", 17000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 36);
        emp.put("salary", 27000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 23);
        emp.put("salary", 13000.00);
        list.add(emp);
        return list;
    }
}
