package moe.sui.dbcontrol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import moe.sui.timecalculate.TimeCalculate;

/**
 * 控制用餐记录生成的类
 * 有三个阶段：1.开始排队，输入用户id，窗口号，进入时间
 * 2.入座 输入用户id，进入时间，座位id,预估离开时间
 *
 */
public class MealRecordController {

    /**
     * 开始排队的方法，也是插入不完整用餐记录的方法
     * 输入uid，窗口id，进入时间
     * 如果新插入成功，则返回true
     */
    public static boolean startQueue(int User_userId, int Window_winId, Date enterTime) throws Exception {

        String string_enterTime = TimeCalculate.dateToString(enterTime);
        String sql_startQueue ="insert into MealRecord (User_userId,Window_winId,enterTime) values" +
                "(" + User_userId + "," + Window_winId +  ",'" + string_enterTime + "')";
        Connection connection=DBconnect.getConnection();
        Statement statement_setMR = connection.createStatement();
        if(statement_setMR.executeUpdate(sql_startQueue)>0){
            return true;
        }
        else return false;
    }

    /**
     * 坐下，插入完整数据
     * 输入UID int，窗口id int，进入时刻（Date），当前时刻(Date)，座椅行int，座椅列int，预估时间（单位分钟，类型为int）
     * 和需求不一样的是进入时刻和当前时刻都要输入，这点调用时需要保存好用户进入时间
     * （或者直接把currentTime和enterTime合在一起，排队到入座之间的时间会对离开时间造成误差）
     */
    public static boolean sitDown(int User_userId,int Window_winId,Date enterTime,Date currentTime,int seat_column,int seat_row,
                              int predicMinute) throws Exception
    {
        int Position_posId = DiningHallController.getPosByWindow(Window_winId);
        int Seat_seatId = DiningHallController.getSeatId(Position_posId,seat_column,seat_row);
        String leaveTime = TimeCalculate.dateAfterMinute(currentTime,predicMinute);

        String sql_sitDown ="update MealRecord set Seat_seatId='" + Seat_seatId + "', leaveTime='" +leaveTime+"' "+
                "where User_userId =" + User_userId + " and enterTime ='" + enterTime + "'";
        if(isMealRecordExist(User_userId,TimeCalculate.dateToString(enterTime))){}
        else { return false; }

        Connection connection=DBconnect.getConnection();
        Statement statement_setMR = connection.createStatement();
        if(statement_setMR.executeUpdate(sql_sitDown)>0){
            return true;
        }
        else return false;
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
    public static boolean isMealRecordExist(int User_userId,String enterTime) throws Exception {
       String queryMealRecord = "select * from MealRecord "+
                "where User_userId=" +User_userId+" and enterTime='"+enterTime+"'";
        Connection connection=DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(queryMealRecord);
        if(resultSet.next()) return true;
        else return false;
    }
}
