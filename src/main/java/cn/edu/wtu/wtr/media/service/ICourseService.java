package cn.edu.wtu.wtr.media.service;

import cn.edu.wtu.wtr.media.util.education.model.Course;

import java.util.List;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-20:54
 * @since 2021-03-15-20:54
 */
public interface ICourseService {

    /**
     * 获取验证码图片
     *
     * @return 验证码
     */
    byte[] checkImage();

    /**
     * 加载学生课表
     *
     * @param sid       学号
     * @param password  密码
     * @param checkCode 验证码
     * @param xn        学年
     * @param xq        学期
     * @return list
     */
    List<Course> loadCourse(String sid, String password, String checkCode, String xn, String xq);
}
