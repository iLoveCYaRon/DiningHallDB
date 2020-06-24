package moe.sui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.HashSet;
import java.util.Set;

import moe.sui.dbcontrol.DBconnect;
import moe.sui.dbcontrol.UserController;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        new Thread(new Runnable() {//测试
            @Override
            public void run() {
                DBconnect.setDBLink("192.168.56.133:3306","user","password");
                try {
                    System.out.println(UserController.Register("xx","17012345678","4455522","rwasdwa","88888888"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();//测试
        initUI();
    }

    /**
     * 把当前文本框中的内容按照Key-Value对的形式存储到本地文件中
     * 在点击登录按钮前会保存，最终传递给接口的参数从本地文件中读取
     */
    private void saveConfig() {
        SharedPreferences.Editor editor = getSharedPreferences("sqlConfig", MODE_PRIVATE).edit();
        EditText editText = findViewById(R.id.sql_addr);
        editor.putString("sqlAddr", editText.getText().toString());
        editText = findViewById(R.id.sql_port);
        editor.putString("sqlPort", editText.getText().toString());
        editText = findViewById(R.id.sql_username);
        editor.putString("sqlUsername",editText.getText().toString());
        editText = findViewById(R.id.sql_password);
        editor.putString("sqlPassword", editText.getText().toString());
        editor.apply();
    }

    private void readConfig(){
        SharedPreferences pref = getSharedPreferences("sqlConfig", MODE_PRIVATE);
        String s = pref.getString("sqlAddr", "");
        ((EditText)findViewById(R.id.sql_addr)).setText(pref.getString("sqlAddr", ""));
        ((EditText)findViewById(R.id.sql_port)).setText(pref.getString("sqlPort", ""));
        ((EditText)findViewById(R.id.sql_username)).setText(pref.getString("sqlUsername", ""));
        ((EditText)findViewById(R.id.sql_password)).setText(pref.getString("sqlPassword", ""));
    }

    private void initUI() {
        //设置顶栏样式
        QMUITopBar topBar = findViewById(R.id.topbar_config);
        topBar.setTitle("食堂管理系统");
        //暂时提供右上角主界面临时入口
        topBar.addRightTextButton("主界面", R.id.empty_view_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),MainActivity.class));
            }
        });

        //读取数据填入到文本框中
        readConfig();
        //绑定按钮点击事件
        QMUIRoundButton button = findViewById(R.id.btn_saveConfig);
        button.setOnClickListener(v -> {
            saveConfig();
            SharedPreferences pref = getSharedPreferences("sqlConfig", MODE_PRIVATE);
            String sqlAddr = pref.getString("sqlAddr", null);
            String sqlPort = pref.getString("sqlPort", null);
            String sqlUsername = pref.getString("sqlUsername", null);
            String sqlPassword = pref.getString("sqlPassword", null);
            if (sqlAddr!=null&&sqlPort!=null&&sqlPassword!=null&&sqlUsername!=null) {
                //调用连接方法
                String sqlURL = sqlAddr + ":" + sqlPort;
            } else {
                Toast.makeText(getApplicationContext(), "数据库配置不完整", Toast.LENGTH_SHORT).show();
            }
        });
    }

}