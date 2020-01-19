package test;

import model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class demo {

    SqlSession session = null;

    @Before
    public void before() throws IOException {
        System.out.println("获取session");
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sessionFactory.openSession();
    }

    @After
    public void after() {
        System.out.println("关闭session");
        session.close();
    }

    //添加用户
    @Test
    public void test1() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sessionFactory.openSession();
        User user = new User("superadmin", "superadmin");
        int i = session.insert("insertUser", user);
        session.commit();
        System.out.println("===i=" + i);
        System.out.println("用户的id : " + user.getId());
    }

    //删除用户
    @Test
    public void test2() throws IOException {
        int i = session.delete("deleteUser", "superadmin");
        session.commit();
        System.out.println("i=" + i);
    }

    //更新用户
    @Test
    public void test3() throws IOException {
        User user = new User();
        user.setId(1);
        user.setPassword("root");
        int i = session.update("updateUser", user);
        session.commit();
        System.out.println("更改的行号:" + i);
    }
    //查找用户
    @Test
    public void test4() throws IOException{
        List<User> users = session.selectList("findUserByName", "y");
        System.out.println(users);
    }
}
