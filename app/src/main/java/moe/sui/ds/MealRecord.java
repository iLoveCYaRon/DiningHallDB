package moe.sui.ds;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.sql.Date;

/* -----------------------
   就餐表 拥有属性
   就餐ID 开始时间 座位ID 窗口号 离开时间
   ----------------------- */
@Table(database = DiningHallDB.class)
public class MealRecord extends BaseModel {

    @ForeignKey(stubbedRelationship = true)
    @PrimaryKey
    private User uid;

    @PrimaryKey
    private java.sql.Date beginTime;

    @ForeignKey(stubbedRelationship = true)
    private Position recPos;

    @ForeignKey(stubbedRelationship = true)
    private Seat recSeatId;

    @Column
    private java.sql.Date endTime;

    public User getUid() {
        return uid;
    }

    public void setUid(User uid) {
        this.uid = uid;
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

    public Position getRecPos() {
        return recPos;
    }

    public void setRecPos(Position recPos) {
        this.recPos = recPos;
    }

    public Seat getRecSeatId() {
        return recSeatId;
    }

    public void setRecSeatId(Seat recSeatId) {
        this.recSeatId = recSeatId;
    }
}
