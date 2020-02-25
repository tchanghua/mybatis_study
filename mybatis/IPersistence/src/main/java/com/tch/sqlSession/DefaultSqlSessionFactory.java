package com.tch.sqlSession;

import com.tch.po.Configuration;

/**
 * @ClassName:DefaultSqlSessionFactory
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/2/23
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession opSession() {
        return new DefaultSqlSession(configuration);
    }
}