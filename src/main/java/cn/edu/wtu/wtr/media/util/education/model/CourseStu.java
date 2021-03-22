package cn.edu.wtu.wtr.media.util.education.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 课程学生信息
 */
@Data
public class CourseStu {
    /**
     * 学号
     */
    @JSONField(name = "XH_ID")
    private String sid;
    /**
     * 姓名
     */
    @JSONField(name = "XM")
    private String name;
    /**
     * 班级名称
     */
    @JSONField(name = "BJMC")
    private String className;
    /**
     * 学年
     */
    @JSONField(name = "XNM")
    private String years;

    /**
     * 学期名称 3货12
     */
    @JSONField(name = "XQM")
    private String term;
}
