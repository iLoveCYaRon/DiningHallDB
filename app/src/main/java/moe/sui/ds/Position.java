package moe.sui.ds;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/* -----------------------
   所有用餐地点的信息 预先登记式
   理由：用于座位表和服务表中, 记录座位所在地点和上班地点？
   拥有属性：地点ID 地点所在楼名称 所在楼层
   ----------------------- */
@Table(database = DiningHallDB.class)
public class Position {
    @PrimaryKey
    private int posId;

    @Column
    private String posName;

    // 楼层 大于零 相同posName的floor值唯一
    @Column
    private int floor;

    public int getPosId() {
        return posId;
    }

    public void setPosId(int posId) {
        this.posId = posId;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
