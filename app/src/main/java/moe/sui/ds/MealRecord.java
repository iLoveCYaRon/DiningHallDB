package moe.sui.ds;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.sql.Date;

/* -----------------------
   就餐表 拥有属性
   就餐ID 开始时间 就餐地点 座位号 离开时间
   ----------------------- */
@Table(database = DiningHallDB.class, allFields = true)
public class MealRecord {

    @PrimaryKey
    @ForeignKey(tableClass = User.class)
    private long userId;

    @PrimaryKey
    private java.sql.Date beginTime;

    @Column
    private String position;

    @Column
    private String seat;

    @Column
    private java.sql.Date endTime;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
