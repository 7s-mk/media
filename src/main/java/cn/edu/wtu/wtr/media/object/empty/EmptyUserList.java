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
     * @return 字符串
     */
    public String toView() {
        StringBuilder builder = new StringBuilder();
        list.forEach(emptyUser -> {
            builder.append("<p><a ").append("href='/user/info/").append(emptyUser.getId()).append("' >")
                    .append(emptyUser.getName()).append("</a></p>");
        });
        return builder.toString();
    }
}
