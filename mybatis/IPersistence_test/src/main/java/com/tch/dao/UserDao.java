package com.tch.dao;

import com.tch.user.User;

import java.util.List;

/**
 * @ClassName:UserDao
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/2/23
 */
public interface UserDao {

    public List<User> findAll();

    public User selectOne(User user);

    public int insert(User user);

    public int update(User user);

    public int delete(User user);
}
