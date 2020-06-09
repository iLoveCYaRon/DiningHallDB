package moe.sui.ds;

import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;

/* -----------------------
   就餐记录表 拥有属性
   userId 就餐位置 座位号 日期
   ----------------------- */
@Table(database = DiningHallDB.class, allFields = true)
public class MealRecord {

    @PrimaryKey
    @ForeignKey(tableClass = User.class)
    private long userId;

    @PrimaryKey
    private String position;
    @PrimaryKey
    private String seat;
    @PrimaryKey
    private java.sql.Date date;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long recordId) {
        this.userId = recordId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }
}
