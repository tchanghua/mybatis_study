package com.tch.sqlSession;


import com.tch.po.Configuration;
import com.tch.po.MapperStatememt;

import java.lang.reflect.*;
import java.util.List;

/**
 * @ClassName:DefaultSqlSession
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/2/23
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    private SimpleExecute simpleExecute = new SimpleExecute();

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    private MapperStatememt getMapperStatement(String statementId){
        return configuration.getMapperStatememtMap().get(statementId);
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {

//        MapperStatememt mapperStatememt = configuration.getMapperStatememtMap().get(statementId);
        MapperStatememt mapperStatememt = getMapperStatement(statementId);
        List<E> list = simpleExecute.query(configuration, mapperStatememt, params);
        return list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> list = selectList(statementId, params);
        if(list.size() == 1){
            return  (T) list.get(0);
        }else{
            throw  new RuntimeException("查询结果为空或者数据过多");
        }
    }

    @Override
    public int insert(String statementId, Object... params) throws Exception {
        MapperStatememt mapperStatememt = getMapperStatement(statementId);
        return simpleExecute.insert(configuration,mapperStatememt,params);
    }

    @Override
    public int update(String statementId, Object... params) throws Exception {
        MapperStatememt mapperStatememt = getMapperStatement(statementId);
        return simpleExecute.update(configuration,mapperStatememt,params);
    }

    @Override
    public int delete(String statementId, Object... params) throws Exception {
        MapperStatememt mapperStatememt = getMapperStatement(statementId);
        return simpleExecute.delete(configuration,mapperStatememt,params);
    }

    @Override
    public <T> T getMapper(Class<T> mapperClass) {
        //使用jdk动态代理为dao接口生成代理对象，并返回
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object o = null;
                // 底层都还是去执行JDBC代码 //根据不同情况，来调用selctList或者selectOne
                // 准备参数 1：statmentid :sql语句的唯一标识：namespace.id= 接口全限定名.方法名
                // 方法名：findAll
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statememtId = className +"." +methodName;
                //准备参数2：param：args
                if(methodName.startsWith("select")) {
                    //获取被调用方法的返回值类型
                    Type genericReturnType = method.getGenericReturnType();
                    //判断是否进行了泛型类型参数化
                    if (genericReturnType instanceof ParameterizedType) {
                        List<Object> objects = selectList(statememtId, args);
                        o =  objects;
                    }else{
                        o = selectOne(statememtId, args);
                    }
                }else if(methodName.startsWith("insert")){
                   o = insert(statememtId, args);
                }else if(methodName.startsWith("update")){
                    o = update(statememtId,args);
                }else if (methodName.startsWith("delete")){
                    o = delete(statememtId,args);
                }
                return o;
            }
        });
        return (T) proxyInstance;
    }
}