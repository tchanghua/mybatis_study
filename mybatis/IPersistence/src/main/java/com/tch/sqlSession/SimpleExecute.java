package com.tch.sqlSession;

import com.tch.config.BoundSql;
import com.tch.po.Configuration;
import com.tch.po.MapperStatememt;
import com.tch.utils.GenericTokenParser;
import com.tch.utils.ParameterMapping;
import com.tch.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:DefaultExecute
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/2/23
 */
public class SimpleExecute implements Execute {
    @Override
    public <E> List<E> query(Configuration configuration, MapperStatememt mapperStatememt, Object... params) throws Exception {
        /*//获取数据库驱动类，获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2. 获取sql语句 : select * from user where id = #{id} and username = #{username}
        //转换sql语句： select * from user where id = ? and username = ? ，转换的过程中，还需要对#{}里面的值进行解析存储
        String oldSql = mapperStatememt.getSql();
        BoundSql boundSql = getBoundSql(oldSql);
        //3.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //4.设置参数
        //4.1获取参数类型
        String paramterType = mapperStatememt.getParamterType();
        Class<?> paramClass = getParamClass(paramterType);
        //4.2设置参数
        List<ParameterMapping> list = boundSql.getList();
        for (int i = 0; i < list.size(); i++) {
            ParameterMapping parameterMapping = list.get(i);
            String content = parameterMapping.getContent();
            //反射
            Field declaredField = paramClass.getDeclaredField(content);
            //暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i+1,o);
        }*/
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mapperStatememt, params);
        //5.执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        //6.封装返回值
        String resultType = mapperStatememt.getResultType();
        Class<?> resultClass = getParamClass(resultType);
        ArrayList<Object> objects = new ArrayList<>();
        //遍历resultSet
        while (resultSet.next()){
            //实例化对象
            Object o = resultClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount() ; i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                //字段值
                Object value = resultSet.getObject(columnName);
                //使用反射或内省，根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName,resultClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
            }
            //将值放到list中
            objects.add(o);
        }

        return (List<E>) objects;
    }

    @Override
    public int insert(Configuration configuration, MapperStatememt mapperStatememt, Object... params) throws  Exception {
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mapperStatememt, params);
        int i = preparedStatement.executeUpdate();
        return  i;
    }

    @Override
    public int update(Configuration configuration, MapperStatememt mapperStatememt, Object... params) throws  Exception {
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mapperStatememt, params);
        int i = preparedStatement.executeUpdate();
        return  i;
    }

    @Override
    public int delete(Configuration configuration, MapperStatememt mapperStatememt, Object... params) throws  Exception {
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mapperStatememt, params);
        int i = preparedStatement.executeUpdate();
        return  i;
    }

    private Class<?> getParamClass(String paramterType) throws ClassNotFoundException {
        if(paramterType != null && !"".equals(paramterType)){
            Class<?> typeClass = Class.forName(paramterType);
            return typeClass;
        }
        return  null;
    }

    private BoundSql getBoundSql(String oldSql) {
        //标记处理器：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{","}", parameterMappingTokenHandler);
        //获取解析出来的sql
        String parseSql = genericTokenParser.parse(oldSql);
        //#{}里解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }

    private PreparedStatement getPreparedStatement(Configuration configuration, MapperStatememt mapperStatememt,Object... params) throws Exception {
        //获取数据库驱动类，获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2. 获取sql语句 : select * from user where id = #{id} and username = #{username}
        //转换sql语句： select * from user where id = ? and username = ? ，转换的过程中，还需要对#{}里面的值进行解析存储
        String oldSql = mapperStatememt.getSql();
        BoundSql boundSql = getBoundSql(oldSql);
        //3.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //4.设置参数
        //4.1获取参数类型
        String paramterType = mapperStatememt.getParamterType();
        Class<?> paramClass = getParamClass(paramterType);
        //4.2设置参数
        List<ParameterMapping> list = boundSql.getList();
        for (int i = 0; i < list.size(); i++) {
            ParameterMapping parameterMapping = list.get(i);
            String content = parameterMapping.getContent();
            //反射
            Field declaredField = paramClass.getDeclaredField(content);
            //暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i+1,o);
        }
        return preparedStatement;
    }
}