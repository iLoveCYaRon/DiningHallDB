package moe.sui.dbcontrol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import moe.sui.datastruct.DineTraffic;
import moe.sui.datastruct.LineTraffic;
import moe.sui.timecalculate.TimeCalculate;

/**
 * 用户查询使用，从数据库查询当前排队情况，用餐情况的类
 */
public class GetTrafficMonitor {
    /**
     * 查询当前正在恰饭的人数，新增的人数，食堂名，记录的更新时间
     * 输出List<Traffic>
     *
     */
    public static List<DineTraffic> getTrafficDining() throws Exception {
        String sql_getDining = "select * from TrafficMonitoring_1";
        Connection connection = DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql_getDining);
        List<DineTraffic> DTList =new ArrayList<>();
        int increaseNum;
        int currentNum;
        String posName;
        String updateTime;
        int posId;
        while(resultSet.next()) {
            updateTime = resultSet.getString("timeMonitor");
            increaseNum = resultSet.getInt("increaseDiners");
            currentNum = resultSet.getInt("currentDiners");
            posId = resultSet.getInt("Position_posId");
            posName =  DiningHallController.getPosNameByPosId(posId);

            DTList.add(new DineTraffic(increaseNum,currentNum,posName,updateTime,posId));
        }
        return DTList;
    }

    /**
     * 获取所有排队人数，窗口名，记录的更新时间
     *
     */
    public static List<LineTraffic> getTrafficLining(int posId) throws Exception {

        String sql_getLining = "select * from TrafficMonitoring_2";
        Connection connection = DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql_getLining);
        List<LineTraffic> LTList =new ArrayList<>();
        int increaseNum;
        int currentNum;
        String winName;
        String updateTime;
        while(resultSet.next()) {
            //如果当前的行里的窗口，不是指定地点的话，则忽略
            if(posId==DiningHallController.getPosByWindow(resultSet.getInt("Window_winId"))) {
                updateTime = resultSet.getString("timeMonitor");
                currentNum = resultSet.getInt("lineNumber");
                winName =  DiningHallController.getWinNameByWinId(resultSet.getInt("Window_winId"));
                LTList.add(new LineTraffic(currentNum,winName,updateTime));
            }
        }
        return LTList;
    }
}
