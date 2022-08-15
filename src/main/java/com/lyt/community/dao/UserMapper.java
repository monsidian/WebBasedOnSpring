package com.lyt.community.dao;

import com.lyt.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

// Mapper只需要写接口不用写实现类，再在xml配置文件中指定方法的标签
// 将接口交给spring管理，类似于@Repository
@Mapper
public interface UserMapper {
    // 根据账号查询用户
    User selectById(int id);

    // 根据用户名查询用户
    User selectByName(String username);

    // 根据邮箱查询用户
    User selectByEmail(String email);

    // 新建一个用户，返回插入表的行数
    int insertUser(User user);

    //修改用户信息,返回修改的行数
    int updateStatus(int id, int status);

    //更新头像
    int updateHeader(int id, String headerUrl);

    //更改密码
    int updatePassword(int id, String password);

}
