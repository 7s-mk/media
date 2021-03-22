package cn.edu.wtu.wtr.media.util.education.login;

import cn.edu.wtu.wtr.media.util.education.exception.JWException;
import cn.edu.wtu.wtr.media.util.education.model.Course;
import cn.edu.wtu.wtr.media.util.education.model.CourseJSON;
import cn.edu.wtu.wtr.media.util.education.model.CourseStu;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析课表
 */
public class JWTools {
    /**
     * 获取来自于<a href="http://jwglxt.wtu.edu.cn/kbcx/xskbcx_cxXsKb.html?gnmkdm=N253508">教务系统课表</a>的数据
     * <p>包含如下参数：</p>
     * <p>&su=学号</p>
     * <p>post xnm 学年</p>
     * <p>post xqm 学期</p>
     *
     * @param json JSON字符串
     * @return list Course
     */
    public static List<Course> analyzeCourse(String json) {
        try {
            if (json == null || json.isEmpty())
                return new ArrayList<>();
            // 解析课程表
            List<CourseJSON> courseJSONSs = JSONObject.parseObject(json).getJSONArray("kbList").toJavaList(CourseJSON.class);
            // 转换成普通课表
            return Course.build(courseJSONSs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JWException("课表解析失败!" + e.getMessage());
        }
    }


    /**
     * 解析学生信息
     *
     * @param json 同{@link #analyzeCourse(String)}
     * @return stu
     */
    public static CourseStu analyzeCourseStu(String json) {
        try {
            if (json == null || json.isEmpty())
                return null;
            return JSONObject.parseObject(json).getJSONObject("xsxx").toJavaObject(CourseStu.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JWException("学生信息解析失败!" + e.getMessage());
        }
    }
}
