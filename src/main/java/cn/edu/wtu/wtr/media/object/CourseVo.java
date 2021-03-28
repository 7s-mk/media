package cn.edu.wtu.wtr.media.object;

import cn.edu.wtu.wtr.media.util.education.model.Course;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-22:28
 * @since 2021-03-15-22:28
 */
@Data
public class CourseVo {
    /**
     * 课表id
     */
    private Integer cid;

    /**
     * 用户id
     */
    private Integer id;
    /**
     * 学号
     */
    private String sid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 学年
     */
    private String year;

    /**
     * 学期
     */
    private String term;
    /**
     * bm
     */
    private String depart;

    /**
     * 课程表
     */
    List<Course> courses = new ArrayList<>();

    /**
     * 更新时间
     */
    private LocalDateTime dateTime;

    /**
     * 显示为卡片
     *
     * @param course 课程
     * @return 卡片
     */
    public static String toView(Course course) {
        return "<p>[课程]:" + course.getName() + "</p>" +
                "<p>[地点]:" + course.getCampus() + course.getLocal() + "</p>" +
                "<p>[周期]:" + course.getWeekDesc() + "</p>" +
                "<p>[老师]:" + course.getTeacher() + "</p>" +
                "<p>[考试方式]:" + course.getTestType() + "</p>";
    }

    /**
     * 群体
     *
     * @param infos infos
     * @return vos
     */
    public static List<CourseVo> build(List<Courseinfo> infos) {
        List<CourseVo> courseVos = new ArrayList<>();
        if (infos != null) {
            infos.forEach(courseinfo -> courseVos.add(build(courseinfo)));
        }
        return courseVos;
    }

    /**
     * po 2 vo
     *
     * @param info 信息
     * @return VO
     */
    public static CourseVo build(Courseinfo info) {
        CourseVo courseVo = new CourseVo();
        if (info != null) {
            courseVo.setCid(info.getCid());
            courseVo.setId(info.getId());
            courseVo.setSid(info.getSid());
            courseVo.setName(info.getName());
            courseVo.setDepart(info.getDepart());
            courseVo.setDateTime(info.getDateTime() == null ? LocalDateTime.now() : info.getDateTime());
            if (info.getCourses() != null) {
                try {
                    courseVo.setCourses(JSONArray.parseArray(info.getCourses(), Course.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            courseVo.setYear(info.getYear());
            courseVo.setTerm(info.getTerm());
        }
        return courseVo;
    }
}
