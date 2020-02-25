package com.tch.config;

import com.tch.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:BoundSql
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/2/23
 */
public class BoundSql {
    private String sqlText;//转换后的sql
    private List<ParameterMapping> list = new ArrayList<>();//存放参数信息

    public BoundSql(String sqlText, List<ParameterMapping> list) {
        this.sqlText = sqlText;
        this.list = list;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getList() {
        return list;
    }

    public void setList(List<ParameterMapping> list) {
        this.list = list;
    }
}