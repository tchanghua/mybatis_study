package com.tch.sqlSession;

import com.tch.po.MapperStatememt;
import sun.net.ftp.FtpDirEntry;

import java.util.List;

/**
 * @ClassName:SqlSession
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/2/23
 */
public interface SqlSession {

    //查询所有
    public <E> List<E> selectList(String statementId,  Object ... params) throws Exception;

    //根据条件查询一条数据
    public <T> T selectOne(String statementId,  Object ... params) throws Exception;

    //新增数据
    public int  insert(String statementId,  Object ... params) throws Exception;

    //修改数据
    public int update(String statementId,  Object ... params) throws Exception;

    //删除数据
    public int delete(String statementId,  Object ... params) throws Exception;


    public <T> T getMapper(Class<T> mapperClass);
}
