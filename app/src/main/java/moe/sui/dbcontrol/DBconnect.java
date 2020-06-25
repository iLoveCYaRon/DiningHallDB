package moe.sui.dbcontrol;

import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import moe.sui.R;


public class DBconnect {


    private static String driver= "com.mysql.jdbc.Driver";
    private static String url=null;
    private static String user=null;
    private static String pwd=null;
    private static final String databaseName ="/dininghall";
    private static final String SSLTZ ="?useSSL=false&serverTimezone=GMT";

    public static void setDBLink(String ip_port,String db_login_usrname,String db_login_pwd) {
        url ="jdbc:mysql://"+ip_port+databaseName+SSLTZ;
        user = db_login_usrname;
        pwd =db_login_pwd;
    }

    public static Connection getConnection() throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, pwd);
    }

}

