package moe.sui.ds;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = DiningHallDB.class)
public class Seat extends BaseModel {
    @PrimaryKey(autoincrement = true)
    private long seatId;

    @Column
    private int row;

    @Column
    private int column;

    @ForeignKey(stubbedRelationship = true)
    private Position seatPos;

    public long getSeatId() {
        return seatId;
    }

    public void setSeatId(long seatId) {
        this.seatId = seatId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Position getSeatPos() {
        return seatPos;
    }

    public void setSeatPos(Position seatPos) {
        this.seatPos = seatPos;
    }
}
