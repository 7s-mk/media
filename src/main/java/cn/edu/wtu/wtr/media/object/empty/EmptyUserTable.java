package cn.edu.wtu.wtr.media.object.empty;

import cn.edu.wtu.wtr.media.object.CourseVo;
import cn.edu.wtu.wtr.media.object.WeekImage;
import cn.edu.wtu.wtr.media.util.education.model.Course;
import lombok.Data;

import java.util.List;

/**
 * 描述： 空课表工具
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-16-14:09
 * @since 2021-03-16-14:09
 */
@Data
public class EmptyUserTable {
    /**
     * 空课表工具(节-周)  不含周末
     */
    private EmptyUserList[][] empty = new EmptyUserList[5][5];

    public EmptyUserTable() {
        for (int i = 0; i < 5; i++) {
            empty[i] = new EmptyUserList[5];
            for (int j = 0; j < 5; j++)
                empty[i][j] = new EmptyUserList();
        }
    }

    public static EmptyUserTable build(List<CourseVo> courseVos) {
        EmptyUserTable emptyUserTable = new EmptyUserTable();
        if (courseVos != null) {
            courseVos.forEach(emptyUserTable::add);
        }
        return emptyUserTable;
    }

    /**
     * 转换成日期表
     *
     * @return 日期表
     */
    public WeekImage toWeekView() {
        WeekImage weekImage = new WeekImage();
        for (int time = 0; time < 5; time++) {
            for (int week = 0; week < 5; week++)
                if (empty[time][week] != null)
                    weekImage.add(week + 1, toTime(time), empty[time][week].toView());
        }
        return weekImage;
    }

    private int toTime(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 5;
            case 4:
                return 7;
            default:
                return 0;
        }
    }

    public void add(CourseVo courseVo) {
        EmptyUser emptyUser = new EmptyUser(courseVo.getId(), courseVo.getSid(), courseVo.getName(), null);
        // 填充
        for (int time = 0; time < 5; time++)
            for (int week = 0; week < 5; week++)
                empty[time][week].add(emptyUser);
        // 排除
        List<Course> courses = courseVo.getCourses();
        if (courses != null)
            courses.forEach(course -> {
                toRange(course.getTime(), course.getDay() - 1, emptyUser);
            });
    }


    /**
     * 排除
     *
     * @param range     节次
     * @param day       星期
     * @param emptyUser 学生
     */
    private void toRange(Course.Range range, int day, EmptyUser emptyUser) {
        // 不考虑周末
        if (day > 4)
            return;
        for (int i = range.getMin(); i <= range.getMax(); i++) {
            switch (i) {
                case 1:
                case 2:
                    empty[0][day].remove(emptyUser);
                    break;
                case 3:
                case 4:
                case 5:
                    empty[1][day].remove(emptyUser);
                    break;
                case 6:
                case 7:
                    empty[2][day].remove(emptyUser);
                    break;
                case 8:
                case 9:
                    empty[3][day].remove(emptyUser);
                    break;
                case 10:
                case 11:
                case 12:
                    empty[4][day].remove(emptyUser);
                    break;
            }
        }
    }
}
