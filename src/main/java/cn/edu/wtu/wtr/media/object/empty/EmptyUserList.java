package cn.edu.wtu.wtr.media.object.empty;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 空课表
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-16-14:09
 * @since 2021-03-16-14:09
 */
@Data
public class EmptyUserList {
    private List<EmptyUser> list = new ArrayList<>();

    public void add(EmptyUser emptyUser) {
        list.add(emptyUser);
    }

    public boolean remove(EmptyUser emptyUser) {
        return list.remove(emptyUser);
    }

    /**
     * 转换成显示字符串
     *
     * @return 字符串
     */
    public String toView() {
        StringBuilder builder = new StringBuilder();
        list.forEach(emptyUser -> builder.append(textName(emptyUser)));
        return builder.toString();
    }

    /**
     * 获取显示名称
     *
     * @param emptyUser 空课表用户信息
     * @return 名称字符串
     */
    private String textName(EmptyUser emptyUser) {
        // top
        String text = "<p><a href='/user/info/" + emptyUser.getId();
        // year
        if (emptyUser.getYear() != null && emptyUser.getTerm() != null) {
            text += "?year=" + emptyUser.getYear() + "&term=" + emptyUser.getTerm();
        }
        // end
        text += "'>" + emptyUser.getName() + "</a></p>";
        return text;
    }
}
