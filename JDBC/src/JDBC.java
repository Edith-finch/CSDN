/*通过jdbc技术对于数据库进行操作
  主要的步骤如下：
  1.导入需要操作的数据库jdbc驱动包
  2.将jdbc包加载至JVM中
  3.通过DriverManager类中getConnection方法获取到对应数据库连接对象
  4.编写需要操作的sql语句
  5.通过连接对象创建编译对象
  6.接受并处理返回的结果
  7.释放资源*/
import java.sql.*;

public class JDBC {
    public static void main(String[] args) {
        //声明变量
        Connection connection = null;
        Statement statement = null;

        try {
            //将jdbc包加载至JVM中
            Class.forName("com.mysql.cj.jdbc.Driver");
            //声明连接数据库的url(本处使用的是本地的MySQL数据库)
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123a123a");
            //编写需要操作的sql语句
            String sql = "insert into user(id,username,password) values(6,'admin','admin')";
            //通过连接对象创建编译对象
            statement = connection.createStatement();
            //向编译对象发出sql指令
            statement.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            //释放资源
            if(statement!=null){
                try{
                    statement.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}