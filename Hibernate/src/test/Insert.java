package test;

import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Insert {
    public static void main(String[] args) {
        //获取SessionFactory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        //通过SessionFactory获取Session
        Session session = sessionFactory.openSession();
        //在Session基础上开启一个事务
        session.beginTransaction();

        //通过调用Session的save方法把对象保存到数据库,插入一条数据
        User user = new User();
        user.setUsername("superadmin");
        user.setPassword("superadmin");
        session.save(user);

        //提交事物
        session.getTransaction().commit();

        //关闭事物
        session.close();
        sessionFactory.close();
    }
}
