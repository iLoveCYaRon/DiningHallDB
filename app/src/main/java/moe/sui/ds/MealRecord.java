package moe.sui.ds;

import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;

/* -----------------------
   就餐记录表 拥有属性
   就餐记录Id 位置 座位号 日期
   ----------------------- */
@Table(database = DiningHallDB.class, allFields = true)
public class MealRecord {

    @PrimaryKey(autoincrement = true)
    private long recordId;

    @NotNull
    private String position;
    @NotNull
    private String seat;
    @NotNull
    private Date date;

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
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

    public void setDate(Date date) {
        this.date = date;
    }
}
