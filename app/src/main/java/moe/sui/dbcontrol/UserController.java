package moe.sui.dbcontrol;

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
     * 验证一个手机号码是否在数据库中存在
     * @param contact 手机号码
     * @return 当手机号存在时返回值为真
     */
    public static boolean contact_validator(String contact) throws Exception {
        Connection connection=DBconnect.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from student where contact="+contact;
        ResultSet resultSet = statement.executeQuery(sql);

        if(resultSet==null) return true;
        else return false;
    }


}
