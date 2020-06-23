package moe.sui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITopBar;

public class AccManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_manage);

        initTopBar();
    }

    private void initTopBar() {
        QMUITopBar topBar = findViewById(R.id.topbar_acc_manage);
        topBar.setTitle("登录");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}