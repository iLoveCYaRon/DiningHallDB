package moe.sui.dbcontrol;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import moe.sui.ds.MealRecord;
import moe.sui.ds.MealRecord_Table;
import moe.sui.ds.Seat;
import moe.sui.ds.Seat_Table;

/**
 * 座位控制器类，负责
 * @author Kotori
 * @time 2020/06/18 17:14
 */

public class SeatController {

    /**
     *
     * @return 返回空座位列表
     */
    public static List<Seat> getAvailableSeat() {
        List<MealRecord> recordList = SQLite.select()
                                    .from(MealRecord.class)
                                    .where(MealRecord_Table.endTime.eq(new Date(-1)))
                                    .queryList();

        // 所有座位列表
        List<Seat> seatList = SQLite.select()
                .from(Seat.class)
                .queryList();
        List<Long> seatIdList = new ArrayList<>();
        for (Seat seat:seatList) {
            seatIdList.add(seat.getSeatId());
        }

        // 对于每一条忙记录，都从所有座位列表中移除其座位
        List<Seat> busySeat = new ArrayList<>();
        for (MealRecord record : recordList) {
            long busySeatId = record.getRecSeatId().getSeatId();
            if(seatIdList.indexOf(busySeatId) != -1) {
                seatList.remove(seatIdList.indexOf(busySeatId));
            }
        }

        return seatList;
    }

}
