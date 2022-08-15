package com.lyt.community.service;

import com.lyt.community.dao.DiscussPostMapper;
import com.lyt.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit){
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
