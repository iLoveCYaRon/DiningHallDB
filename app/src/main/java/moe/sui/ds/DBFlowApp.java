package moe.sui.ds;

import android.app.Application;

import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class DBFlowApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(FlowConfig.builder(this)
                        .addDatabaseConfig(DatabaseConfig.builder(DiningHallDB.class)
                        .databaseName("DiningHallDatabase")
                        .build())
                        .build());
    }
}
