package com.lyt.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration  //表明该类是一个配置类
public class AlphaConfig {

    @Bean   //定义第三方的bean，bean的名字即是方法的名字，表明该方法返回的对象将被装入容器中
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat(("yyyy-MM-dd HH:mm:ss"));
    }
}
