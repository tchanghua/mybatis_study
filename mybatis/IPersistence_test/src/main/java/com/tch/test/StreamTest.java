package com.tch.test;


import com.tch.dao.UserDao;
import com.tch.io.Resource;
import com.tch.sqlSession.SqlSession;
import com.tch.sqlSession.SqlSessionFactory;
import com.tch.sqlSession.SqlSessionFactoryBuilder;
import com.tch.user.User;
import java.io.InputStream;
import java.util.List;

/**
 * @ClassName:StreamTest
 * @Description: TODO
 * @Auth: tch
 * @Date: 2020/2/22
 */
public class StreamTest {

    public static void main(String[] args) throws Exception {
        InputStream resourceAsStream = Resource.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().builder(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.opSession();
        User user = new User();
        user.setId(1L);
        user.setName("tom");
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        int num = 0;
        //查询所有
//        List<User> all = userDao.findAll();
//        for (User u:all ) {
//            System.out.println(u.toString());
//        }
//
//        System.out.println("-----------------------------------------");
//        //查询单个
        User user1 = userDao.selectOne(user);
        System.out.println(user1);
        //删除
         //num = userDao.delete(user);
        //新增
//        num = userDao.insert(user);
        //修改
        num = userDao.update(user);
        System.out.println("num:" + num);

    }
}