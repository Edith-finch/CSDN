package test;

import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Select {
    public static void main(String[] args) {
        //获取SessionFactory
        SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
        //通过SessionFactory获取Session
        Session session=sessionFactory.openSession();
        //在Session基础上开启一个事务
        session.beginTransaction();

        //查询方式get
        User user = (User) session.get(User.class, 4);
        System.out.println("get：id=4的用户名：" + user.getUsername());

        //查询方式load
        User user2 = (User) session.load(User.class, 5);
        System.out.println("load：id=5的用户名：" + user2.getUsername());

        //提交事物
        session.getTransaction().commit();
        //关闭事物
        session.close();
        sessionFactory.close();
    }
}
