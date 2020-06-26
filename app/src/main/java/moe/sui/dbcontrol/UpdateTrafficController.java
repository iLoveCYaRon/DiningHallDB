package moe.sui.dbcontrol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import moe.sui.datastruct.DineTraffic;
import moe.sui.timecalculate.TimeCalculate;

/**
 * 更新人流记录
 */
public class UpdateTrafficController {
    /**
     * 更新TrafficMonitoring_1表
     * 无输入 无输出
     * 从原TM1表查询currentDiners,Window_winId
     *  getPosIdByWinId(Window_winId)
     *  要在TM1表中更新指定地点的 TimeMonitor,increaseDiner,currentDiner
     *
     */
    public static void UpdateDiningTraffic() throws Exception {
        String sql_getAllPosId = "select * from Position";
        Connection connection = DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql_getAllPosId);

        String currentTime = TimeCalculate.dateToString(new Date());
        int posId;
        int new_currentDiners;
        int new_increaseDiners;

        while(resultSet.next()) {//while 对所有找到的位置进行遍历
            posId = resultSet.getInt("posId");
            new_currentDiners = countCurrentDine(posId,currentTime);//通过cCD方法获取正在用餐人数
            new_increaseDiners = calDineIncrease(new_currentDiners);//通过cDI方法计算新增人数
            updateTMDine(currentTime,new_increaseDiners,new_currentDiners,posId);
        }
    }

    /**
     * 更新TrafficMonitoring_2表
     */
    /**
     * 计算指定窗口人数
     * 返回需要插入Traffic1表的currentNum
     */
    public static void UpdateLiningTraffic() throws Exception {
        String sql_getAllWinId = "select * from Window";
        Connection connection = DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql_getAllWinId);

        String currentTime = TimeCalculate.dateToString(new Date());
        int winId;
        int new_lineNumber;

        while(resultSet.next()) {//while 对所有找到的窗口号进行遍历
            winId = resultSet.getInt("winId");
            new_lineNumber = countCurrentLine(winId);//通过cCL方法获取正在排队人数
            updateTMLine(currentTime,new_lineNumber,winId);
        }
    }

    /**
     * 计算指定窗口排队的人数
     */
    public static int countCurrentLine(int winId) throws Exception {
        String sql_Count_Line ="select count(Window_winId) from MealRecord "+
                "where Window_winId = "+winId+"and Seat_seatId = null";
        Connection connection = DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql_Count_Line);
        while(resultSet.next()){
            return resultSet.getInt(1);
        }
        return -1;
    }
/**--------------------------------------------------
    /**
     * 计算指定地点的符合正在用餐的人数
     *
     */
    public static int countCurrentDine(int posId,String currentTime) throws Exception {
        String sql_get_Window = "select Window_winId from MealRecord " +
                "where leaveTime > '" + currentTime + "'";
        Connection connection = DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql_get_Window);
        int count = 0;
        while (resultSet.next()) {
            if (posId == DiningHallController.getPosByWindow(resultSet.getInt("Window_winId"))) {
                count++;
            }
        }
        return count;
    }

    /**
     *计算新增恰饭人数
     */
    public static int calDineIncrease(int new_curr) throws Exception {
        String sql_get_Old_Curr ="select currentDiners from TrafficMonitoring_1 ";
        Connection connection = DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql_get_Old_Curr);
        while(resultSet.next()) {
            return new_curr-resultSet.getInt("currentDiners");
        }
        return -1;
    }
    /**
     * 更新TrafficMonitor1表
     */
    public static void updateTMDine(String timeMonitor,int increaseDiners,int currentDiners,int Position_posId) throws Exception {
        String sql_test_Pos_inTM1_Exist="select * from TrafficMonitoring_1 "+
                "where Position_posId=" + Position_posId;
        String sql_update ="update TrafficMonitoring_1 set "+
                "timeMonitor='"+timeMonitor+"' "+
                "increaseDiners="+increaseDiners+" "+
                "currentDiners="+currentDiners+" "+
                "where Position_posId="+Position_posId;
        String sql_insert = "insert into TrafficMonitoring_1 values('"+timeMonitor+"',"
                +increaseDiners+","+currentDiners+","+Position_posId+")";
        Connection connection = DBconnect.getConnection();
        Statement statement_Query = connection.createStatement();
        Statement statement_Execute = connection.createStatement();
        ResultSet resultSet = statement_Query.executeQuery(sql_test_Pos_inTM1_Exist);
        if(resultSet.next()){
            statement_Execute.executeUpdate(sql_update);
        }
        else{
            statement_Execute.executeUpdate(sql_insert);
        }
    }

    /**
     *更新TM2表
     * 若无记录则插入记录
     * 有记录则更新记录
     */
    public static void updateTMLine(String timeMonitor,int lineNumber,int Window_winId) throws Exception {
        String sql_test_Pos_inTM1_Exist="select * from TrafficMonitoring_2 "+
                "where Window_winId=" + Window_winId;
        String sql_update ="update TrafficMonitoring_2 set "+
                "timeMonitor='"+timeMonitor+"' "+
                "lineNumber="+lineNumber+" "+
                "where Window_winId="+Window_winId;
        String sql_insert = "insert into TrafficMonitoring_1 values('"+timeMonitor+"',"
                +lineNumber+","+Window_winId+")";
        Connection connection = DBconnect.getConnection();
        Statement statement_Query = connection.createStatement();
        Statement statement_Execute = connection.createStatement();
        ResultSet resultSet = statement_Query.executeQuery(sql_test_Pos_inTM1_Exist);
        if(resultSet.next()){
            statement_Execute.executeUpdate(sql_update);
        }
        else{
            statement_Execute.executeUpdate(sql_insert);
        }
    }

}
