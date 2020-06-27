package moe.sui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.sql.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import moe.sui.dbcontrol.DBconnect;
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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initTopBar();
    }

//    public void toRegister(View v){
//        Intent intent =new Intent(this,RegisterActivity.class);
//        startActivity(intent);
//    }
//    public void tryLogin(View v){
//        EditText phone_num =(EditText)findViewById (R.id.phone_num);
//        String validator=phone_num.getText().toString();
//        if(moe.sui.dbcontrol.UserController.validator(validator)) {
//            Intent intent =new Intent(this,LoginedActivity.class);
//            startActivity(intent);
//            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(MainActivity.this, "登录失败，请检查填写信息", Toast.LENGTH_SHORT).show();
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        initTopBar();
    }

    /**
     * 清除数据库表数据
     */
    private void databaseReset() {
        Delete.table(MealRecord.class);

        Delete.table(ServiceWindow.class);

        Delete.table(Seat.class);

        Delete.table(Service.class);

        Delete.table(Position.class);

        Delete.table(User.class);
    }

    /**
     * 插入示例数据
     */
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


    }

    @SuppressLint("ResourceAsColor")
    void initTopBar() {
        QMUITopBar topBar = findViewById(R.id.topbar_main);
        topBar.setTitle("主界面");
        topBar.removeAllLeftViews();
        Button leftButton = topBar.addLeftTextButton("登录", R.id.empty_view_button);
        leftButton.setTextColor(R.color.colorBackground);
        leftButton.setOnClickListener(v -> {
            if(getSharedPreferences("account", MODE_PRIVATE).getBoolean("status", false)) {
                Toast.makeText(getApplicationContext(), "您已成功登录过，无需再次登录", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(getApplicationContext(), AccManageActivity.class));
        });

        // 更新主界面UID
        if(getSharedPreferences("account", MODE_PRIVATE).getBoolean("status", false)) {
            TextView text = findViewById(R.id.text_userInfo);
            SharedPreferences pref = getSharedPreferences("account", MODE_PRIVATE);
            String content = "UID: " + String.valueOf(pref.getInt("uid", 0)) + "\n登录账户：" + pref.getString("username", "");
            text.setText(content);
        }

    }

    @OnClick(R.id.btn_addMealRecord) void toRecordActivity() {
        startActivity(new Intent(getApplicationContext(), RecordActivity.class));
    }

    @OnClick(R.id.btn_viewTraffic) void toViewPosActivity() {
        startActivity(new Intent(getApplicationContext(), ViewPosActivity.class));
    }
}
