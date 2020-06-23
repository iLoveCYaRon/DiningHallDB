package moe.sui.dbcontrol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DBconnect {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://192.168.56.133:3306/test1?useSSL=false&serverTimezone=GMT";
    //private static final String url = "jdbc:mysql://192.168.56.133:3306/test1";
    private static final String user = "testcus";
    private static final String pwd = "88888888";
    public static Connection conn;

    public static void linkMysql() {
//        Connection conn=null;
        PreparedStatement stmt=null;
        try {
            Class.forName(driver).newInstance();
            System.out.println("驱动加载成功！！！！！");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try{
            conn = DriverManager.getConnection(url,user,pwd);
            System.out.println("连接数据库成功！！！！！！");
            String sql = "select * from user";//查询语句
            stmt= conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                String id=rs.getString("id");
                String name=rs.getString("name");
                String phone=rs.getString("phone");
                System.out.println(id+"\t"+name+"\t"+phone);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(conn!=null){
                try {
                    conn.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}

