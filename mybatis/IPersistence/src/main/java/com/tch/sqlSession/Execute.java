package com.tch.sqlSession;

import com.tch.po.Configuration;
import com.tch.po.MapperStatememt;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:Execute
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/2/23
 */
public interface Execute {

    public <E> List<E> query(Configuration configuration, MapperStatememt mapperStatememt ,Object ... params) throws  Exception;

    public int insert(Configuration configuration, MapperStatememt mapperStatememt ,Object ... params) throws  Exception;

    public int update(Configuration configuration, MapperStatememt mapperStatememt ,Object ... params) throws  Exception;

    public int delete(Configuration configuration, MapperStatememt mapperStatememt ,Object ... params) throws  Exception;

}
