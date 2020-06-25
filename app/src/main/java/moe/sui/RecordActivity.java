package moe.sui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.qmuiteam.qmui.widget.QMUITopBar;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordActivity extends AppCompatActivity {

    @BindView(R.id.spin_pos)
    Spinner mPosSpinner;

    @BindView(R.id.spin_window)
    Spinner mWinSpinner;

    private HashMap<Integer, String> posMap;
    private List<Integer> winList;

    private int maxRow;
    private int maxColumn;

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
            //获取食堂可用哈希表

            //发送信息通知UI

            //获取窗口

            //发送信息通知UI
        }).start();


    }

    @SuppressLint("HandlerLeak")
    Handler spinnerHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //更新地点列表

                case 2:
                    //更新窗口列表
            }
        }
    };
}

