package moe.sui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUITopBar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import moe.sui.datastruct.Position;
import moe.sui.datastruct.Window;
import moe.sui.dbcontrol.DiningHallController;
import moe.sui.dbcontrol.MealRecordController;

public class RecordActivity extends AppCompatActivity {

    @BindView(R.id.spin_pos)
    Spinner mPosSpinner;

    @BindView(R.id.spin_window)
    Spinner mWinSpinner;

    @BindView(R.id.spin_during_time)
    Spinner mTimeSpinner;

    @BindView(R.id.record_column)
    EditText mRecordColumn;

    @BindView(R.id.record_row)
    EditText mRecordRow;

    private HashMap<String, Integer> posMap;
    private List<Window> winList;

    private int maxRow = 0;
    private int maxColumn = 0;

    private static String mDateFormat = "yyyy-MM-dd HH:mm:ss";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        QMUITopBar topBar = findViewById(R.id.topbar_record);
        topBar.setTitle("填写就餐信息");
        topBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
        new Thread(() -> {
            try {
                //获取食堂可用哈希表
                posMap = DiningHallController.getAllPosition();
                //获取窗口
                winList = DiningHallController.getAllWindow();
                //发送信息通知UI
                Message msg = new Message(); msg.what = 1; spinnerHandler.sendMessage(msg);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //Spinner加载监听事件
    @OnItemSelected(R.id.spin_pos)
    void onPosChanged(){
        String search = mPosSpinner.getSelectedItem().toString();
        if(posMap.get(search)!=null) {
            new Thread(() -> {
                try {
                    //根据地点获取窗口列表
                    winList = DiningHallController.getWindowByPos(posMap.get(search));
                    //获取行列限制
                    Map<String,Integer> range = DiningHallController.getSeatRange(posMap.get(search));
                    maxRow = range.get("row"); maxColumn = range.get("column");
                    //发送信息通知UI
                    Message msg = new Message(); msg.what = 2; spinnerHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler spinnerHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //更新地点列表
                    List<String> posNameList = new ArrayList<>(posMap.keySet());
                    ArrayAdapter posAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,posNameList);
                    mPosSpinner.setAdapter(posAdapter);
                    posAdapter.notifyDataSetChanged();
                    //更新窗口列表
                    List<String> winNameList = new ArrayList<>();
                    for ( Window window : winList) {
                        winNameList.add(window.winName);
                    }
                    ArrayAdapter winAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,winNameList);
                    mWinSpinner.setAdapter(winAdapter);
                    winAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    //更新窗口列表
                    List<String> winName2List = new ArrayList<>();
                    for ( Window window : winList) {
                        winName2List.add(window.winName);
                    }
                    ArrayAdapter win2Adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,winName2List);
                    mWinSpinner.setAdapter(win2Adapter);
                    win2Adapter.notifyDataSetChanged();
            }
        }
    };

    //设置提交按钮点击事件
    @OnClick(R.id.btn_addMealRecord)
    public void addMealRecord() {
        int uid = validateStatus();
        if(uid!=-1) {
            int winId = winList.get(mWinSpinner.getSelectedItemPosition()).winId;
            SharedPreferences pref = getSharedPreferences("record",MODE_PRIVATE);
            new Thread(() -> {
                try {
                    //获取保存时间，如果不存在则两个时间都用当前时间
                    String saveTime = pref.getString("time", null);
                    Date curr = new Date();
                    int duringTime = Integer.parseInt(mTimeSpinner.getSelectedItem().toString());
                    //暂不限制输入
                    int column = Integer.parseInt(mRecordColumn.getText().toString());
                    int row = Integer.parseInt(mRecordRow.getText().toString());
                    if (saveTime==null) {
                        MealRecordController.sitDown(uid,winId,curr,curr,column,row,duringTime);
                    } else {
                        DateFormat df = new SimpleDateFormat(mDateFormat);
                        Date save = df.parse(saveTime); //字符串转时间
                        MealRecordController.sitDown(uid,winId,save,curr,column,row,duringTime);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.remove("time"); editor.apply();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            Toast.makeText(getApplicationContext(),"登录状态异常",Toast.LENGTH_SHORT).show();
        }
    }

    //设置暂存按钮点击事件
    @OnClick(R.id.btn_saveMealRecord)
    public void saveMealRecord() {
        int uid = validateStatus();
        if(uid!=-1) {
            int winId = winList.get(mWinSpinner.getSelectedItemPosition()).winId;
            SharedPreferences.Editor pref = getSharedPreferences("record",MODE_PRIVATE).edit();
            new Thread(() -> {
                try {
                    //保存好时间 用于下次插入完整记录
                    Date curr = new Date();
                    DateFormat df = new SimpleDateFormat(mDateFormat);
                    String str = df.format(curr); //时间转字符串
                    pref.putString("time",str);
                    pref.apply();
                    //插入记录
                    MealRecordController.startQueue(uid,winId,curr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            Toast.makeText(getApplicationContext(),"登录状态异常",Toast.LENGTH_SHORT).show();
        }
    }

    private int validateStatus() {
        SharedPreferences pref = getSharedPreferences("account",MODE_PRIVATE);
        if(pref.getBoolean("status",false)) {
            return pref.getInt("uid", -1);
        }
        return -1;
    }
}

