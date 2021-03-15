package cn.edu.wtu.wtr.media.util.education.model;

import lombok.Data;

/**
 * 描述： 老师
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-18:53
 * @since 2021-03-15-18:53
 */
@Data
public class Teacher {
    /**
     * 姓名
     */
    private String name;
    /**
     * 职务
     */
    private String position;

    public Teacher() {
    }

    public Teacher(String name, String position) {
        this();
        this.name = name;
        this.position = position;
    }
}
