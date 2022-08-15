package com.lyt.community.service;

import com.lyt.community.dao.UserMapper;
import com.lyt.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id){
        // 根据ID查找用户
        return userMapper.selectById(id);
    }
}
