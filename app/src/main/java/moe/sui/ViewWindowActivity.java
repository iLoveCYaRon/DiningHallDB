package moe.sui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ViewWindowActivity extends AppCompatActivity {

    public static void actionStart(Context context, int productID) {
        Intent intent = new Intent(context, ViewWindowActivity.class);
        intent.putExtra("ID", productID);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_window);


    }
}