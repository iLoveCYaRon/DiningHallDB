package moe.sui.ds;

import com.raizlabs.android.dbflow.annotation.Database;
@Database(version = DiningHallDB.version, foreignKeyConstraintsEnforced = true)
public class DiningHallDB {
    public static final int version = 1;
}

