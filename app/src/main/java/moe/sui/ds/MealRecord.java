package moe.sui.ds;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.sql.Date;

/* -----------------------
   就餐表 拥有属性
   就餐ID 开始时间 座位ID 窗口号 离开时间
   ----------------------- */
@Table(database = DiningHallDB.class, allFields = true)
public class MealRecord {

    @PrimaryKey
    @ForeignKey(tableClass = User.class)
    private long userId;

    @PrimaryKey
    private java.sql.Date beginTime;

    @ForeignKey(tableClass = Position.class)
    private int posId;

    @ForeignKey(tableClass = Seat.class)
    private String seatId;

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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getPosId() {
        return posId;
    }

    public void setPosId(int posId) {
        this.posId = posId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
}
