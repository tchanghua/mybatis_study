package com.tch.sqlSession;

import com.tch.config.XmlConfigBuilder;
import com.tch.po.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @ClassName:SqlSessionFactoryBulider
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/2/23
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory builder(InputStream in) throws PropertyVetoException, DocumentException {
        //使用dom4j解析配置文件到configuration中
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);
        //第二：创建sqlSessionFactory对象：工厂类：生产sqlSession:会话对象
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return  sqlSessionFactory;
    }
}