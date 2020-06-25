package moe.sui.dbcontrol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 控制用餐记录生成的类
 * 有三个阶段：1.开始排队，输入用户id，窗口号，进入时间
 * 2.入座 输入用户id，进入时间，座位id,预估离开时间
 *
 */
public class MealRecordController {


    public static int startQueue(int User_userId,int Window_winId,String enterTime) throws Exception {


        String sql_startQueue ="insert into MealRecord (User_userId,Window_winId,enterTime) values" +
                "(" + User_userId + "," + Window_winId +  ",'" + enterTime + "')";
        Connection connection=DBconnect.getConnection();
        Statement statement_setMR = connection.createStatement();
        if(statement_setMR.executeUpdate(sql_startQueue)>0){
            return 1;
        }
        else return -1;
    }

    public static int sitDown(int User_userId,String enterTime,int Seat_seatId,String leaveTime) throws Exception {

        String sql_sitDown ="update MealRecord set Seat_seatId='" + Seat_seatId + "', leaveTime='" +leaveTime+"' "+
                "where User_userId =" + User_userId + " and enterTime ='" + enterTime + "'";
        Connection connection=DBconnect.getConnection();
        Statement statement_setMR = connection.createStatement();
        if(statement_setMR.executeUpdate(sql_sitDown)>0){
            return 1;
        }
        else return -1;
    }

    public static int leaveSeat(int User_userId,String enterTime,String leaveTime) throws Exception {

        String sql_sitDown ="update MealRecord set leaveTime='" + leaveTime + "' " +
                "where User_userId =" + User_userId + " and enterTime ='" + enterTime + "'";
        Connection connection=DBconnect.getConnection();
        Statement statement_setMR = connection.createStatement();
        if(statement_setMR.executeUpdate(sql_sitDown)>0){
            return 1;
        }
        else return -1;
    }
    
//    public static int setMealRecord(int User_userId,int Window_winId,int Seat_seatId,String enterTime,String leaveTime)throws Exception {
//
//        String sql_mealRecord_Insert = "insert into MealRecord (User_userId,Window_winId,Seat_seatId,enterTime) values" +
//                "(" + User_userId + "," + Window_winId + "," + Seat_seatId + ",'" + enterTime + "')";//入座，除离开时间都插入
//        String sql_leaveTime_update = "update MealRecord set leaveTime='" + leaveTime + "' " +
//                "where User_userId =" + User_userId + " and enterTime ='" + enterTime + "'";//离座，更新离开时间
//        String sql;
//        Connection connection=DBconnect.getConnection();
//        Statement statement_setMR = connection.createStatement();
//
//
//        if(isMealRecordExist(User_userId,enterTime)){//如果记录已经存在，更新离开时间
//            sql=sql_leaveTime_update;
//        }
//        else {                                       //如果记录不存在，则insert
//            sql=sql_mealRecord_Insert;
//        }
//
//        if(statement_setMR.executeUpdate(sql)>0){
//            return 1;
//        }
//        else return -1;
//
//    }

    //检查是否已经存在用餐记录
//    public static boolean isMealRecordExist(int User_userId,String enterTime) throws Exception {
//        String queryMealRecord = "select * from MealRecord "+
//                "where User_userId=" +User_userId+" and enterTime='"+enterTime+"'";
//        Connection connection=DBconnect.getConnection();
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(queryMealRecord);
//        if(resultSet.next()) return true;
//        else return false;
//    }
}
