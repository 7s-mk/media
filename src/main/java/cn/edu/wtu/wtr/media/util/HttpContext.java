package cn.edu.wtu.wtr.media.util;

import cn.edu.wtu.wtr.media.object.Office;
import cn.edu.wtu.wtr.media.object.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述： 请求上下文
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-02-28-15:26
 * @since 2021-02-28-15:26
 */
public class HttpContext {
    /**
     * 获取当前 request 对象
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

    /**
     * 获取当前 response 对象
     *
     * @return response
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getResponse();
    }

    /**
     * 获取域对象
     *
     * @param name name
     * @return 域
     */
    public static Object get(String name) {
        return getRequest() == null ? null : getRequest().getSession().getAttribute(name);
    }

    /**
     * 设置域对象
     *
     * @param name 域
     * @param obj  obj
     */
    public static void set(String name, Object obj) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().setAttribute(name, obj);
        }
    }

    /**
     * 移除域对象
     *
     * @param name name
     */
    public static void remove(String name) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().removeAttribute(name);
        }
    }

    /**
     * 设置用户
     *
     * @param user 用户
     */
    public static void setUser(User user) {
        set("user", user);
    }

    /**
     * 获取登录的用户
     *
     * @return 用户
     */
    public static User getUser() {
        return get("user") == null ? null : (User) get("user");
    }

    /**
     * 移除登录的用户
     */
    public static void logout() {
        remove("user");
    }

    /**
     * 获取登录用户的身份
     *
     * @return 身份
     */
    public static Office getOffice() {
        User user = getUser();
        if (user != null)
            return user.office();
        return Office.未登录;
    }

    /**
     * 检验当前登录用户的权限是否比这个大
     *
     * @param office 权限
     * @return 是否
     */
    public static boolean checkOffice(Office office) {
        return getOffice().compareTo(office) >= 0;
    }
}
