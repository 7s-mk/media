package cn.edu.wtu.wtr.media.object.empty;

import cn.edu.wtu.wtr.media.util.education.model.Course;
import lombok.Data;

/**
 * 描述： 空课表
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-16-14:09
 * @since 2021-03-16-14:09
 */
@Data
public class EmptyUser {
    /**
     * id
     */
    private int id;
    /**
     * 学号
     */
    private String sid;
    /**
     * 姓名
     */
    private String name;
    /**
     * 周期
     */
    private Course.Range week;

    public EmptyUser() {
    }

    public EmptyUser(int id, String sid, String name, Course.Range week) {
        this.id = id;
        this.sid = sid;
        this.name = name;
        this.week = week;
    }
}
