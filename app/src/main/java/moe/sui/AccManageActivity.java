package moe.sui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moe.sui.dbcontrol.DBconnect;
import moe.sui.dbcontrol.UserController;
import moe.sui.ds.Position;

public class AccManageActivity extends AppCompatActivity {
    @BindView(R.id.login_username) EditText mLoginUsername;
    @BindView(R.id.login_pswd) EditText mLoginPassword;
    @BindView(R.id.regis_contact) EditText mRegContact;
    @BindView(R.id.regis_pswd) EditText mRegPassword;
    @BindView(R.id.regis_id) EditText mRegId;
    @BindView(R.id.regis_name) EditText mRegName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_manage);

        ButterKnife.bind(this);
        initUI();
    }

    /**
     * 初始化顶栏，以及恢复上一次登录的文本，测试当前登录态
     */
    private void initUI() {
        QMUITopBar topBar = findViewById(R.id.topbar_acc_manage);
        topBar.setTitle("登录");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences pref = getSharedPreferences("account", MODE_PRIVATE);
        mLoginUsername.setText(pref.getString("username", ""));
        mLoginPassword.setText(pref.getString("password", ""));

        //检查登录态
        login();
    }

    /**
     * 登录按钮点击事件
     */
    @OnClick(R.id.btn_login)
    public void login() {
        //根据当前输入内容调用登录方法
        if(mLoginUsername.getText().length()>=11) {
            new Thread(() -> {
                try {
                    int status = UserController.contactLogin(mLoginUsername.getText().toString(), mLoginPassword.getText().toString());
                    Message msg = new Message(); msg.what = status;
                    loginHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            new Thread(() -> {
                try {
                    int status = UserController.uIDLogin(mLoginUsername.getText().toString(), mLoginPassword.getText().toString());
                    Message msg = new Message(); msg.what = status;
                    loginHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 接收登录的返回信息
     */
    @SuppressLint("HandlerLeak")
    private Handler loginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String notice = "";
            switch (msg.what) {
                case -1:
                    notice = "服务器暂时有些问题，稍等下再试试";
                    break;
                case -2:
                    notice = "登录失败，用户不存在";
                    break;
                case -3:
                    notice = "登录失败，密码错误";
                    break;
                default:
                    notice = "登录成功";
                    //保存此次登录用户名密码
                    SharedPreferences.Editor pref = getSharedPreferences("account", MODE_PRIVATE).edit();
                    pref.putBoolean("status", true);
                    pref.putString("username", mLoginUsername.getText().toString());
                    pref.putString("password", mLoginPassword.getText().toString());
                    pref.putInt("uid", msg.what);
                    pref.apply();
            }
            Toast.makeText(getApplicationContext(), notice, Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 注册按钮点击事件
     */
    @OnClick(R.id.btn_regis)
    public void register() {
        //根据当前输入内容调用注册方法
        EditText editText = findViewById(R.id.regis_pswd_re);
        if(mRegPassword.getText().equals(editText.getText())&&!mRegPassword.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mRegContact.getText().toString().length()==11) {
            new Thread(() -> {
                try {
                    int status = UserController.register(mRegName.getText().toString(), mRegContact.getText().toString(),
                            mRegId.getText().toString(),null,mRegPassword.getText().toString());
                    Message msg = new Message(); msg.what = status;
                    regHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            Toast.makeText(getApplicationContext(),"请输入正确的手机号",Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler regHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what > 0) {
                mLoginUsername.setText(mRegContact.getText());
                mLoginPassword.setText(mRegPassword.getText());
                Toast.makeText(getApplicationContext(),"注册成功，点击登录按钮可登录",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
            }
        }
    };
}