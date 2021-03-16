package cn.edu.wtu.wtr.media.util.education.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：课程对象
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-18:49
 * @since 2021-03-15-18:49
 */
@Slf4j
@Data
public class Course {
    /**
     * 课程号
     */
    private String id;
    /**
     * 课程名称
     */
    private String name;
    /**
     * 老师
     */
    private Teacher teacher;
    /**
     * 校区
     */
    private String campus;
    /**
     * 地点
     */
    private String local;
    /**
     * 星期几
     */
    private int day;
    /**
     * 周数
     */
    private Range week;
    /**
     * 周数详情
     */
    private String weekDesc;
    /**
     * 时间
     */
    private Range time;
    /**
     * 详情安排
     */
    private String desc;
    /**
     * 类型
     */
    private String category;
    /**
     * 考试类型
     */
    private String testType;
    /**
     * 学年
     */
    private String year;
    /**
     * 学期
     */
    private String term;
    /**
     * 更新时间
     */
    private String dateTime;


    /**
     * JSONs 转 Courses
     *
     * @param jsons jsons
     * @return courses
     */
    public static List<Course> build(List<CourseJSON> jsons) {
        List<Course> courses = new ArrayList<>();
        if (jsons != null)
            jsons.forEach(json -> {
                courses.add(build(json));
            });
        return courses;
    }

    /**
     * 使用JSON 构建
     *
     * @param json json
     * @return course
     */
    public static Course build(CourseJSON json) {
        Course course = new Course();
        course.setId(json.getKch_id());
        course.setName(json.getKcmc());
        course.setTeacher(new Teacher(json.getXm(), json.getZcmc()));
        course.setCampus(json.getXqmc());
        course.setLocal(json.getCdmc());
        // 星期
        try {
            course.setDay(Integer.parseInt(json.getXqj()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        course.setWeek(buildWeek(json.getZcd()));
        course.setTime(buildNumber(json.getJcs()));

        // 详情
        course.setWeekDesc(json.getZcd());
        course.setYear(json.getXnm());
        course.setTerm(json.getXqm());

        course.setDesc(json.getKcxszc());
        course.setCategory(json.getKcxz());
        course.setTestType(json.getKhfsmc());

        course.setDateTime(json.getDateDigitSeparator());

        return course;
    }

    /**
     * 解析A-B周
     *
     * @param weekName A-B周
     * @return Range
     */
    public static Range buildWeek(String weekName) {
        try {
            if (weekName != null && weekName.length() > 1) {
                if (!weekName.contains(",")) {
                    return buildNumber(weekName.substring(0, weekName.length() - 1));
                } else {
                    // todo 待解决1-15周,17
                    return buildWeek(weekName.split(",")[0]);
                }
            }
        } catch (Exception e) {
            log.error("解析周数 {} 异常", weekName);
            e.printStackTrace();
        }
        return new Range(0, 0);
    }

    /**
     * A-B范围
     *
     * @param numberName A-B
     * @return Range
     */
    public static Range buildNumber(String numberName) {
        try {
            if (numberName == null || numberName.length() < 1) {
                return new Range();
            }
            if (numberName.contains("-")) {
                String[] numbers = numberName.split("-", 2);
                return new Range(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
            }
            return new Range(Integer.parseInt(numberName), Integer.parseInt(numberName));
        } catch (Exception e) {
            log.error("解析 {} 异常", numberName);
            e.printStackTrace();
            return new Range();
        }
    }

    /**
     * 范围
     */
    @Data
    public static class Range {
        private int min;
        private int max;

        public Range() {
        }

        public Range(int min, int max) {
            this();
            this.min = min;
            this.max = max;
        }

        @Override
        public String toString() {
            return min + "-" + max + "周";
        }
    }
}

