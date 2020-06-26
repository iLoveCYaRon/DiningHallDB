package moe.sui.dbcontrol;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moe.sui.datastruct.Position;
import moe.sui.datastruct.Window;
import moe.sui.ds.MealRecord;

import moe.sui.ds.Seat;


/**
 * 食堂位置控制类
 * @time 2020/06/18 17:14
 */

public class DiningHallController {

/**通过窗口id获取位置id的方法
 * 输入窗口id int
 * return 查询成功时返回地点id  int，出幺蛾子返回-1
 *  int getSeat(int posId)
 */
    public static int getPosByWindow(int winId) throws Exception {
        String sql_find_P_by_W = "select Position_posId from `Window` "+
                "where winId=" + winId;
        Connection connection = DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql_find_P_by_W);
        int PosId;
        while(resultSet.next()) {
            return resultSet.getInt("Position_posId");
        }
        return -1;


    }




/**获取指定地点座位行列范围的方法
 * 输入地点id
 * return 数据结构 :键值对 <"column",行值(int)><"row",列值(int)>
 * map<column,row> getAllSeat(int posId)
 * */
    public static Map<String, Integer> getSeatRange(int posId) throws Exception {
        String sql_count_column = "select count(*) as nums from Seat "
                + "where `row`=1 and Position_posId =" + posId;
        String sql_count_row = "select count(*) as nums from Seat "
                + "where `column`=1 and Position_posId =" + posId;
        Connection connection = DBconnect.getConnection();
        Statement statement_countColumn = connection.createStatement();
        Statement statement_countRow =connection.createStatement();
        ResultSet resultSet_column, resultSet_row;
        resultSet_column = statement_countColumn.executeQuery(sql_count_column);
        resultSet_row = statement_countRow.executeQuery(sql_count_row);
        int column,row;
        Map <String,Integer> map=new HashMap<>();
        while(resultSet_column.next()) {
            column = resultSet_column.getInt(1);
            map.put("column",column);
        }
        while(resultSet_row.next()) {
            row = resultSet_row.getInt(1);
            map.put("row",row);
        }
        return map;

}


/**获取指定地点所有窗口的方法
 * 输入地点id
 * return List<Window>
 * List<Window> getWindowByPos(int posId)
 *
 */
     public static List<Window> getWindowByPos(int posId) throws Exception {
         String sql_getWindow = "select * from `Window` " +
                 "where Position_posId=" + posId;
         List<Window> list = new ArrayList<>();
         Connection connection = DBconnect.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql_getWindow);
         int winId;
         String winName;
         int Position_posId;
         while (resultSet.next()){
             winId=resultSet.getInt("winId");
             winName =resultSet.getString("winName");
             Position_posId = resultSet.getInt("Position_posId");
             list.add(new Window(winId,winName,Position_posId));
         }
         return list;

     }

/**直接获取所有窗口的方法
 * return List<Window>
 *
 */
     public static List<Window>getAllWindow() throws Exception {
         String sql_getWindow = "select * from `Window`";
         List<Window> list = new ArrayList<>();
         Connection connection = DBconnect.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql_getWindow);
         int winId;
         String winName;
         int Position_posId;
         while (resultSet.next()){
             winId=resultSet.getInt("winId");
             winName =resultSet.getString("winName");
             Position_posId = resultSet.getInt("Position_posId");
             list.add(new Window(winId,winName,Position_posId));
         }
         return list;
     }

/**
 * 直接获取所有地点的方法
 * @return HashMap<posName+floor+"层",posId>
 */
     public static HashMap<String, Integer> getAllPosition() throws Exception {
        String sql_getAllPosition = "select * from `Position`";
        HashMap<String,Integer> map=new HashMap<>();
        Connection connection = DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql_getAllPosition);
        int posId;
        String posName;
        int floor;
        while (resultSet.next()){
            posId=resultSet.getInt("posId");
            floor = resultSet.getInt("posFloor");
            posName =resultSet.getString("posName")+floor+"层";
            map.put(posName,posId);
        }
        return map;
    }

    /**
     * Seat cr to id
     * 输入地点码，行，列，返回seatId
     */
    public static int getSeatId(int Position_posId,int column,int row) throws Exception {
        String sql_getSeatId = "select seatId from Seat "+
                "where `Position_posId`=" +Position_posId+" and `column`="+column+
                " and `row`=" + row;
        Connection connection = DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql_getSeatId);
        if(resultSet.next()){ return resultSet.getInt(1);}
        else return -1;
    }

    /**
     * 通过窗口号获取窗口名
     */
    public static String getWinNameByWinId(int winId) throws Exception {
        String sql_find_WN_by_WI = "select winName from `Window` "+
                "where winId=" + winId;
        Connection connection = DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql_find_WN_by_WI);
        String winName = "";
        while(resultSet.next()) {
            winName = resultSet.getString("winName");
        }
        return winName;
    }

    /**
     * 通过地点号获取地点名
     */
    public static String getPosNameByPosId(int posId) throws Exception {
        String sql_find_PN_by_PI = "select posName from `Position` "+
                "where posId=" + posId;
        Connection connection = DBconnect.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql_find_PN_by_PI);
        String posName = "";
        while(resultSet.next()) {
            posName = resultSet.getString("posName");
        }
        return posName;
    }

}
