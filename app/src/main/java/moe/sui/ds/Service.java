package moe.sui.ds;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

@Table(database = DiningHallDB.class)
public class Service {
    @PrimaryKey
    @ForeignKey(tableClass = User.class)
    private long id;

    @NotNull
    @Column
    private String servePos;

    @NotNull
    @Column
    private java.sql.Date serveTime;
}
