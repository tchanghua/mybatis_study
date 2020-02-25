package com.tch.config;

import com.tch.po.Configuration;
import com.tch.po.MapperStatememt;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @ClassName:XmlMapperBuilder
 * @Description: 解析mapper文件
 * @Auth: tch
 * @Date: 2020/2/23
 */
public class XmlMapperBuilder {
    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream in) throws DocumentException {
        Document document = new SAXReader().read(in);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> list = rootElement.selectNodes("//select");
        for (Element element : list) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramterType = element.attributeValue("paramterType");
            String sqlText = element.getTextTrim();
            MapperStatememt mapperStatememt = new MapperStatememt();
            mapperStatememt.setId(id);
            mapperStatememt.setParamterType(paramterType);
            mapperStatememt.setResultType(resultType);
            mapperStatememt.setSql(sqlText);
            String key = namespace + "." + id;
            configuration.getMapperStatememtMap().put(key,mapperStatememt);
        }
    }
}