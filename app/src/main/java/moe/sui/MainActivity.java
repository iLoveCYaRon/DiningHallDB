package moe.sui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
<<<<<<< Updated upstream
=======
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.sql.Date;
import java.util.List;

import moe.sui.dbcontrol.SeatController;
import moe.sui.dbcontrol.UserController;
import moe.sui.ds.MealRecord;
import moe.sui.ds.Position;
import moe.sui.ds.Position_Table;
import moe.sui.ds.Seat;
import moe.sui.ds.Seat_Table;
import moe.sui.ds.Service;
import moe.sui.ds.ServiceWindow;
import moe.sui.ds.User;
import moe.sui.ds.User_Table;
>>>>>>> Stashed changes

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< Updated upstream
=======
        Button button = (Button) this.findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText phone_num =(EditText)findViewById (R.id.phone_num);
                String c=phone_num.getText().toString();

                if(moe.sui.dbcontrol.UserController.validator(c)) {
                    setContentView(R.layout.logined_layout);
                }
            }
        });

        databaseReset();
        databaseInit();
    }

    // 清空数据库
    private void databaseReset() {
        Delete.table(MealRecord.class);

        Delete.table(ServiceWindow.class);

        Delete.table(Seat.class);

        Delete.table(Service.class);

        Delete.table(Position.class);

        Delete.table(User.class);
    }

    // 插入示例数据
    private void databaseInit() {
        // 插入10个用户
        for(int i=0; i<10; i++) {
            User user = new User();
            user.setName("用户" + i);
            user.setContact("1000"+i);
            user.save();
        }

        // 插入就餐地点
        Position position = new Position(); position.setPosName("第一食堂"); position.setFloor(1); position.save();
        position = new Position(); position.setPosName("第一食堂"); position.setFloor(2); position.save();
        position = new Position(); position.setPosName("第一食堂"); position.setFloor(3); position.save();
        position = new Position(); position.setPosName("第二食堂"); position.setFloor(2); position.save();
        position = new Position(); position.setPosName("第二食堂"); position.setFloor(3); position.save();
        position = new Position(); position.setPosName("第二食堂"); position.setFloor(1); position.save();

        // 为第一饭堂一楼添加4x4个座位
        position = SQLite.select().from(Position.class)
                         .where(Position_Table.posName.eq("第一食堂"), Position_Table.floor.eq(1))
                         .querySingle();
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                Seat seat = new Seat();
                seat.setRow(i); seat.setColumn(j); seat.setSeatPos(position);
                seat.save();
            }
        }

        // 为第一饭堂一楼添加八个窗口
        for(int i=1; i<=8; i++) {
            ServiceWindow window = new ServiceWindow();
            window.setWindowPos(position);
            window.setWinName("窗口" + i);
            window.save();
        }

        // 尝试插入一条 当前时间 用户1 第一食堂 一楼 第一窗口 座位行列(1,1) 的就餐记录
        MealRecord record = new MealRecord();
        Date date = new Date(System.currentTimeMillis());
        record.setBeginTime(date);
        record.setUid(SQLite.select().from(User.class).where(User_Table.name.eq("用户1")).querySingle());
        record.setRecPos(position);
        record.setRecSeatId(SQLite.select().from(Seat.class).where(Seat_Table.seatPos_posId.eq(position.getPosId()),Seat_Table.row.eq(1),Seat_Table.column.eq(1)).querySingle());
        record.setEndTime(new Date(-1));
        record.save();

        SeatController.getAvailableSeat();

>>>>>>> Stashed changes
    }
}
