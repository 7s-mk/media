package cn.edu.wtu.wtr.media.util;

import cn.edu.wtu.wtr.media.common.MessageException;
import cn.edu.wtu.wtr.media.util.PopUps;
import cn.edu.wtu.wtr.media.util.education.exception.JWException;
import org.springframework.ui.Model;

/**
 * ----------------
 *
 * @author lpc
 * @version 1.0 2021/3/27
 * ----------------
 * @since 2021/3/27-下午10:25
 */
public class ErrorTools {


    /**
     * 错误的处理发货
     *
     * @param model 错误
     * @param e     e
     * @return 错误处理
     */
    public static String error(Model model, Exception e) {
        return error(model, e, null);
    }


    /**
     * 错误的处理
     *
     * @param model 错误
     * @param e     e
     * @param msg   msg
     * @return 错误处理
     */
    public static String error(Model model, Exception e, String msg) {
        return error(model, e, msg, null);
    }

    /**
     * 错误的处理返回
     *
     * @param url   url
     * @param model 错误
     * @param e     e
     * @param msg   msg
     * @return 错误处理
     */
    public static String error(Model model, Exception e, String msg, String url) {
        if (url == null)
            return PopUps.info(model, errorMsg(e, msg));
        return PopUps.info(model, errorMsg(e, msg), url);
    }

    /**
     * 获取错误中的信息
     *
     * @param e   错误
     * @param msg 自定义信息msg
     * @return 信息
     */
    public static String errorMsg(Exception e, String msg) {
        msg = msg == null ? "" : (msg + " ");
        if (e instanceof MessageException)
            msg += e.getMessage();
        else if (e instanceof JWException)
            msg += e.getMessage();
        return msg;
    }
}
