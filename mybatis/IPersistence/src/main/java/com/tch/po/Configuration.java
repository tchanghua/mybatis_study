package com.tch.po;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:Configuration
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/2/22
 */
public class Configuration {

    private DataSource dataSource;
    private Map<String,MapperStatememt> mapperStatememtMap = new HashMap<String, MapperStatememt>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MapperStatememt> getMapperStatememtMap() {
        return mapperStatememtMap;
    }

    public void setMapperStatememtMap(Map<String, MapperStatememt> mapperStatememtMap) {
        this.mapperStatememtMap = mapperStatememtMap;
    }
}