package com.lyt.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AlphaDAOMyBatisimpl implements AlphaDAO{

    @Override
    public String select() {
        return "MyBatis";
    }
}
