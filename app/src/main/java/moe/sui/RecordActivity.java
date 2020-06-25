package moe.sui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.qmuiteam.qmui.widget.QMUITopBar;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initUI();
    }

    private void initUI() {
        QMUITopBar topBar = findViewById(R.id.topbar_record);
        topBar.setTitle("填写就餐信息");
        topBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
    }
}