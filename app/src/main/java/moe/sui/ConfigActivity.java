package moe.sui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.qmuiteam.qmui.widget.QMUITopBar;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        // 设置顶栏样式
        // TopBar设置
        QMUITopBar topBar = findViewById(R.id.ConfigTopBar);
        topBar.setTitle("食堂管理系统");
    }
}