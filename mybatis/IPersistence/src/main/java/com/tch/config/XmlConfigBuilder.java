package com.tch.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tch.io.Resource;
import com.tch.po.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName:XmlConfigBuilder
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/2/23
 */
public class XmlConfigBuilder {
    private Configuration configuration;

    public XmlConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * @Author tch
     * @Description 用dom4j解析配置文件封装configuration
     * @Date 11:00
     * @Param []
     * @return com.tch.po.Configuration
     **/
    public Configuration parseConfig(InputStream in) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(in);
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//property");

        //解析sqlMapConfig.xml
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driveClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);
        //解析UserMapper
        List<Element> mappers = rootElement.selectNodes("//mapper");
        for (Element element : mappers) {
            String resource = element.attributeValue("resource");
            InputStream inputStream = Resource.getResourceAsStream(resource);
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
            xmlMapperBuilder.parse(inputStream);
        }
        return configuration;
    }
}