package cn.edu.wtu.wtr.media.util;

/**
 * ----------------
 * 常用方法
 *
 * @author lpc
 * @version 1.0 2021/3/27
 * @since 2021/3/27-下午10:35
 */
public class CommonUtils {
    /**
     * 是否是空
     * <p>str==null||str.isEmpty()</p>
     *
     * @param str str
     * @return ok
     */
    public static boolean isNullStr(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 是否是空
     * <p>str!=null&&!str.isEmpty()</p>
     *
     * @param str str
     * @return ok
     */
    public static boolean noNullStr(String str) {
        return !isNullStr(str);
    }
}
