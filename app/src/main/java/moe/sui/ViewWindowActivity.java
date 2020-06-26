package moe.sui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import moe.sui.UIcontrol.ViewWindow;

public class ViewWindowActivity extends AppCompatActivity {

    public static void actionStart(Context context, int posID) {
        Intent intent = new Intent(context, ViewWindowActivity.class);
        intent.putExtra("ID", posID);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_window);

        int productId = getIntent().getIntExtra("ID",0);

        ViewWindow viewWindowFragment = (ViewWindow) getSupportFragmentManager().findFragmentById(R.id.fragment_view_queue);
        viewWindowFragment.refresh(productId);
    }
}