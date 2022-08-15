package com.lyt.community.dao;

import org.springframework.stereotype.Repository;

@Repository("alphaH")
public class AlphaDAOHibernateImpl implements AlphaDAO{
    @Override
    public String select(){

        return "Hibernate";
    }

}
