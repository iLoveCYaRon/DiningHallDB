package moe.sui.dbcontrol;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import moe.sui.ds.User;
import moe.sui.ds.User_Table;

public class UserController {

    // 验证用户输入的手机号
    public static boolean validator(String contact) {
        User user = SQLite.select().from(User.class).where(User_Table.contact.eq(contact)).querySingle();
        return (user != null);
    }


}
