package moe.sui.ds;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.sql.Date;

/* -----------------------
   服务表 记录职工的服务信息，用于追踪接触链
   理由：职工有可能患病，职工所接触的学生也可能患病
   拥有属性：职工ID（来自User表） 服务开始时间 地点 结束时间
   ----------------------- */

@Table(database = DiningHallDB.class)
public class Service {
    @PrimaryKey
    @ForeignKey(tableClass = User.class)
    private long id;

    @PrimaryKey
    private java.sql.Date beginTime;

    @Column
    private String servePos;

    @Column
    private java.sql.Date endTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getServePos() {
        return servePos;
    }

    public void setServePos(String servePos) {
        this.servePos = servePos;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
