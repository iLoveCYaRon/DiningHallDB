package moe.sui.dbcontrol;

import android.print.PrinterInfo;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import moe.sui.ds.User;
import moe.sui.ds.User_Table;

public class UserController {


    /**
     * 注册账号
     * @param contact 手机号码
     * @return 手机号重复返回-2，身份证重复返回-3，学号重复返回-4，注册成功返回uid，其他失败返回-1
     */
    //注册
    public static int register(String name,String contact,String idNum,String address,String password)throws Exception{
        if(isContactExist(contact)){ return -2; }//手机号重复
        if(isIdNumExist(idNum))
        {
            if(idNum.length()==18) return -3;//身份证号重复
            else if(idNum.length()==12) return -4;//学号重复
            else return -1;
        }
        String user = "('"+name+"','"+contact+"','"+idNum+"','"+address+"','"+password+"')";
        String user_nid ="('"+name+"','"+contact+"','"+address+"','"+password+"')";
        Connection connection=DBconnect.getConnection();
        Statement statement_register = connection.createStatement();
        Statement statement_getUID= connection.createStatement();
        String sql_register_id = "insert into User (name,contact,idNum,address,password) values"+user;
        if(idNum==""){System.out.println("空id");}
        else{System.out.println("id非空");}
        if(statement_register.executeUpdate(sql_register_id)>0){
            String sql_getUID="select userID from User where contact="+contact;
            ResultSet resultSet = statement_getUID.executeQuery(sql_getUID);
            resultSet.next();
            int userID = Integer.parseInt(resultSet.getString(1));
            return userID;
            }
        else return -1;
    }
    //使用手机号登录
    public static int contactLogin(String contact,String password) throws Exception {
        Connection connection=DBconnect.getConnection();
        Statement statement = connection.createStatement();
        String IsPwdTrue_sql = "select userId from User where contact="+contact+" and password="+ password;
        ResultSet resultSet;
        if(!isContactExist(contact)) {System.out.println("登录失败，用户不存在");return -2;}
        else{
            resultSet = statement.executeQuery(IsPwdTrue_sql);
            if(!resultSet.next()) {System.out.println("登录失败，密码错误");return -3;}
            else{
                System.out.println("登录成功");
                int i =Integer.parseInt(resultSet.getString(1));
                System.out.println(i);
                return i;

            }
        }
    }
    //使用UID登录
    public static int uIDLogin(String UID,String password) throws Exception {
        Connection connection=DBconnect.getConnection();
        Statement statement = connection.createStatement();
        String IsPwdTrue_sql = "select userId from User where userId="+UID+" and password="+ password;
        ResultSet resultSet;
        if(!isUIDExist(UID)) {System.out.println("登录失败，用户不存在");return -2;}
        else{
            resultSet = statement.executeQuery(IsPwdTrue_sql);
            if(!resultSet.next()) {System.out.println("登录失败，密码错误");return -3;}
            else{
                System.out.println("登录成功");
                int i =Integer.parseInt(resultSet.getString(1));
                System.out.println(i);
                return i;

            }
        }
    }

    //检测手机号是否存在
    public static boolean isContactExist(String contact) throws Exception {
        Connection connection=DBconnect.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from User where contact="+contact;
        ResultSet resultSet = statement.executeQuery(sql);

        if(resultSet.next()) return true;
        else return false;
    }

    public static boolean isUIDExist(String UID)throws Exception{
        Connection connection=DBconnect.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from User where userId="+UID;
        ResultSet resultSet = statement.executeQuery(sql);

        if(resultSet.next()) return true;
        else return false;
    }


    public static boolean isIdNumExist(String id) throws Exception{

        if(id == ""){return false;}
        Connection connection=DBconnect.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from User where idNum="+id;
        ResultSet resultSet = statement.executeQuery(sql);
        //String idN = resultSet.getString(1);
        if(resultSet.next()) return true;
        else return false;
    }
}
