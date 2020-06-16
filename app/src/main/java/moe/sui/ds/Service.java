package moe.sui.ds;

import android.view.Window;

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
    @ForeignKey(stubbedRelationship = true)
    private User uid;

    @ForeignKey(stubbedRelationship = true)
    private ServiceWindow serveWindow;

    @PrimaryKey
    private java.sql.Date beginTime;

    @Column
    private java.sql.Date endTime;

    public User getUid() {
        return uid;
    }

    public void setUid(User uid) {
        this.uid = uid;
    }

    public ServiceWindow getServeWindow() {
        return serveWindow;
    }

    public void setServeWindow(ServiceWindow serveWindow) {
        this.serveWindow = serveWindow;
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
}
