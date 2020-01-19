package test;

import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Delete {
    public static void main(String[] args) {
        //获取SessionFactory
        SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
        //通过SessionFactory获取Session
        Session session=sessionFactory.openSession();
        //Session开启事物
        session.beginTransaction();

        //根据id查询一条记录
        User user = (User) session.get(User.class, 7);
        //删除
        session.delete(user);

        //提交事物
        session.getTransaction().commit();
        //关闭事物
        session.close();
        sessionFactory.close();
    }
}
