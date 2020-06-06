package moe.sui.ds;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.saveable.AutoIncrementModelSaver;
/* -----------------------
   就餐人员表 拥有属性
   就餐ID 姓名 联系方式 身份证号
   ----------------------- */
@Table(database = DiningHallDB.class, allFields = true)
public class User {
    //自增整型主码
    @PrimaryKey(autoincrement = true)
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String contact;
    @NotNull
    private String idNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

}
