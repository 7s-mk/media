package cn.edu.wtu.wtr.media.object;

import cn.edu.wtu.wtr.media.util.education.model.Course;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-23:29
 * @since 2021-03-15-23:29
 */
@Data
public class WeekImage {
    /**
     * 用户id
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
     * 学年
     */
    private String year;
    /**
     * 学期
     */
    private String term;
    /**
     * 物体
     * 第一城是节次  (0 | 1，2，|3|  4，5 |6| 7）
     * 第二层是周期  (0|  1，2，3，4，5,6,7）
     */
    private List<List<String>> obj;

    public WeekImage() {
        obj = new ArrayList<>(8);
        for (int time = 0; time < 8; time++) {
            obj.add(new ArrayList<>());
            // 初始化星期 1-5
            for (int week = 0; week < 8; week++) {
                obj.get(time).add(week == 0 ? timeHeard(time) : fill(time));
            }
        }
    }

    /**
     * 时间名称
     *
     * @param time time
     * @return 字符串
     */
    private String timeHeard(int time) {
        switch (time) {
            case 1:
                return "1-2节";
            case 2:
                return "3-5节";
            case 4:
                return "6-7节";
            case 5:
                return "7-8节";
            case 7:
                return "9-12节";
            default:
                return " ";
        }
    }


    /**
     * 填充
     *
     * @param time 时间
     * @return 填充
     */
    private String fill(int time) {
        switch (time) {
            case 0:
                return "早餐";
            case 3:
                return "午餐";
            case 6:
                return "晚餐";
        }
        return " ";
    }

    /**
     * 添加一个
     *
     * @param week week
     * @param time 节次
     * @param str  显示支持HTML
     */
    public void add(int week, Course.Range time, String str) {
        for (int i = time.getMin(); i <= time.getMax(); i++) {
            addRange(week, i, str);
        }
    }


    /**
     * 自定义添加
     * @param week 周  1-7
     * @param time 时间 1，2，4，5 7
     * @param str srt
     */
    public void add(int week, int time, String str) {
        obj.get(time).set(week, str);
    }

    private void addRange(int week, int time, String str) {
        switch (time) {
            case 1:
            case 2:
                obj.get(1).set(week, str);
                break;
            case 3:
            case 5:
            case 4:
                obj.get(2).set(week, str);
                break;
            case 6:
            case 7:
                obj.get(4).set(week, str);
                break;
            case 8:
            case 9:
                obj.get(5).set(week, str);
                break;
            case 10:
            case 11:
            case 12:
                obj.get(7).set(week, str);
                break;
            default:
        }
    }

}

