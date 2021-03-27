package cn.edu.wtu.wtr.media.util;

import cn.edu.wtu.wtr.media.common.MessageException;
import cn.edu.wtu.wtr.media.object.User;

/**
 * ----------------
 *
 * @author lpc
 * @version 1.0 2021/3/27
 * @since 2021/3/27-下午10:32
 */
public class UserTools {

    public static boolean check(User user) {
        checkType(user);
        return true;
    }

    /**
     * 检验用户格式
     *
     * @param user 用户格式
     */
    public static void checkType(User user) {
        if (user == null)
            throw new MessageException("用户处理错误！");
        if (CommonUtils.isNullStr(user.getUsername()))
            throw new MessageException("用户名不能为空!");
        if (CommonUtils.isNullStr(user.getPassword()))
            throw new MessageException("密码不能为空！");
        if (CommonUtils.isNullStr(user.getName()))
            throw new MessageException("姓名不能为空");
    }
}
