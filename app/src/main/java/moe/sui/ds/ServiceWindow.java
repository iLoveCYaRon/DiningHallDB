package moe.sui.ds;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/* -----------------------
   窗口表 所有食堂窗口的信息 预先登记式
   理由：用于就餐记录和职工上班服务表使用
   拥有属性：窗口ID 窗口名称 窗口所在地点
   ----------------------- */

@Table(database = DiningHallDB.class)
public class ServiceWindow extends BaseModel {

    @PrimaryKey(autoincrement = true)
    private int winId;

    @Column
    private String winName;

    @ForeignKey(stubbedRelationship = true)
    private Position windowPos;

    public int getWinId() {
        return winId;
    }

    public void setWinId(int winId) {
        this.winId = winId;
    }

    public String getWinName() {
        return winName;
    }

    public void setWinName(String winName) {
        this.winName = winName;
    }

    public Position getWindowPos() {
        return windowPos;
    }

    public void setWindowPos(Position windowPos) {
        this.windowPos = windowPos;
    }
}
