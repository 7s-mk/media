package cn.edu.wtu.wtr.media.util.education.model;

import lombok.Data;

/**
 * 描述： 教务系统课程类型的JSON格式
 * <p>对应JSON下的<b>kbList</b>字段List中的每一个</p>
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-18:35
 * @since 2021-03-15-18:35
 */
@Data
public class CourseJSON {
    /**
     * 课程号
     */
    private String kch_id;

    /**
     * 课程名称
     */
    private String kcmc;

    /**
     * 校区
     */
    private String xqmc;
    /**
     * 场地 YG0X-XXX
     */
    private String cdmc;

    /**
     * 周期
     * <b>1-12周</b>
     */
    private String zcd;
    /**
     * 星期几 <b>数字</b>
     */
    private String xqj;


    /**
     * 节次
     * <b>1-3</b>
     */
    private String jcs;


    /**
     * 课程周期安排  讲课 实验
     */
    private String kcxszc;


    /**
     * 课程类别
     * <b>专业课、选修课</b>
     */
    private String kclb;

    /**
     * 课程类型
     * <b>专业必等</b>
     */
    private String kcxz;

    /**
     * 课程考试类型
     */
    private String khfsmc;

    /**
     * 老师名称
     */
    private String xm;

    /**
     * 老师职称
     */
    private String zcmc;

    /**
     * 学年
     */
    private String xnm;
    /**
     * 学期名
     * <b>3是上学期 12 是下学期</b>
     */
    private String xqm;
    /**
     * 更新时间
     * YYYY-MM-DD
     */
    private String dateDigitSeparator;

}
