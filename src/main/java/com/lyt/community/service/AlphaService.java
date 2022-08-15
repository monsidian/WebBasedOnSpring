package com.lyt.community.service;

import com.lyt.community.dao.AlphaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
//@Scope("prototype")
public class AlphaService {

    @Autowired  //将AlphaDAO注入给AlphaService以便调用
    private AlphaDAO alphaDAO;

    public AlphaService(){
        System.out.println("AlphaService Instancing");
    }

    @PostConstruct  //构造对象之后立即调用该方法
    public void init(){
        System.out.println("AlphaService Initializing");
    }

    @PreDestroy     //销毁对象之前调用该方法
    public void destory(){
        System.out.println("destorying");
    }

    public String find(){   //调用alphaDAO实现模拟查询业务
        return alphaDAO.select();
    }
}
