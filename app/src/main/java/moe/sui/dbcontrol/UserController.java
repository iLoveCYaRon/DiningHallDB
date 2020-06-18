package moe.sui.dbcontrol;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import moe.sui.ds.User;
import moe.sui.ds.User_Table;

public class UserController {

    /**
     * 验证一个手机号码是否在数据库中存在
     * @param contact 手机号码
     * @return 当手机号存在时返回值为真
     */
    public static boolean validator(String contact) {
        User user = SQLite.select().from(User.class).where(User_Table.contact.eq(contact)).querySingle();
        return (user != null);
    }


}
