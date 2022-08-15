package com.lyt.community.dao;

import com.lyt.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    // 显示首页的帖子，userId可不需要，为了后续加入个人主页的功能，offset表示查询起始行，limit为每页显示多少帖子
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // @Param用于给参数起一个别名，
    // 当方法有且只有一个参数，且使用在动态条件<if>中，则必须起别名
    int selectDiscussPostRows(@Param("userId") int userId);

}
