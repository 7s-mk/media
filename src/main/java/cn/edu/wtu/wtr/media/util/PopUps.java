package cn.edu.wtu.wtr.media.util;

import cn.edu.wtu.wtr.media.object.Office;
import org.springframework.ui.Model;

/**
 * 描述：弹窗警告
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-02-28-14:33
 * @since 2021-02-28-14:33
 */
public class PopUps {


    /**
     * 权限不足
     *
     * @param model model
     * @return 权限不足提醒
     */
    public static String unOffice(Model model) {
        return unOffice(model, "权限不足");
    }

    /**
     * 权限不足
     *
     * @param msg   提醒
     * @param model model
     * @return 权限不足提醒
     */
    public static String unOffice(Model model, String msg) {
        return info(model, msg);
    }

    /**
     * 权限不足
     *
     * @param office 身份
     * @param model  model
     * @return 权限不足提醒
     */
    public static String unOffice(Model model, Office office) {
        return unOffice(model,
                "权限不足！身份至少必须是：[" + office + "]才能执行此操作\\n当前登录用户身份为：["
                        + HttpContext.getOffice() + "]!");
    }

    /**
     * 未登录提醒 并返回登录页面
     *
     * @param model model
     * @return 登录检查
     */
    public static String unLogin(Model model) {
        return info(model, "请先登录", "/");
    }


    /**
     * 弹窗警告
     *
     * @param msg   弹窗消息
     * @param model model
     * @return 弹窗并返回之前的页面
     */
    public static String info(Model model, String msg) {
        model.addAttribute("code", popCode(msg, "-1"));
        return "code";
    }

    /**
     * 弹窗并跳转指定页面
     *
     * @param model model
     * @param msg   弹窗消息
     * @param url   指定页面
     * @return 弹窗并返回指定页面
     */
    public static String info(Model model, String msg, String url) {
        model.addAttribute("code", popCode(msg, url));
        return "code";
    }


    /**
     * 返回js 弹窗
     *
     * @param msg 消息
     * @return js代码
     */
    public static String popCode(String msg) {
        return popCode(msg, null);
    }

    /**
     * 返回js 弹窗和跳转
     *
     * @param msg 消息
     * @param url 页面
     * @return js代码
     */
    public static String popCode(String msg, String url) {
        StringBuilder code = new StringBuilder("<script type=\"text/javascript\">");
        // 弹窗
        if (msg != null) {
            code.append("alert(\"");
            code.append(msg);
            code.append("\");");
        }
        // 跳转
        if (url != null) {
            switch (url) {
                case "-1":
                    // 返回上一页并刷新
                    code.append("self.location=document.referrer;");
                    break;
                case "-2":
                    // 返回前两页
                    code.append("window.history.go(-2);location.reload();");
                    break;
                default:
                    code.append("location.href=\"").append(url).append("\";");
                    break;
            }
        }
        code.append("</script>");
        return code.toString();
    }
}
