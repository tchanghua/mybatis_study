package com.tch.io;

import java.io.InputStream;

/**
 * @ClassName:Resource
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/2/22
 */
public class Resource {

    public static InputStream getResourceAsStream(String path){
        InputStream resourceAsStream = Resource.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }
}