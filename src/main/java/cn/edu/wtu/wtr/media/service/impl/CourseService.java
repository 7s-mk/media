package cn.edu.wtu.wtr.media.service.impl;

import cn.edu.wtu.wtr.media.service.ICourseService;
import cn.edu.wtu.wtr.media.util.HttpContext;
import cn.edu.wtu.wtr.media.util.education.login.JWClient;
import cn.edu.wtu.wtr.media.util.education.model.Course;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-20:58
 * @since 2021-03-15-20:58
 */
@Service
public class CourseService implements ICourseService {
    /**
     * 获取验证码图片
     *
     * @return 验证码
     */
    @Override
    public byte[] checkImage() {
        JWClient jwClient = HttpContext.getJWClient();
        if (jwClient.open()) {
            return jwClient.checkImage();
        }
        return null;
    }

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
    @Override
    public List<Course> loadCourse(String sid, String password, String checkCode, String xn, String xq) {
        JWClient jwClient = HttpContext.getJWClient();
        if (jwClient.login(sid, password, checkCode)) {
            return jwClient.getCourses(xn, xq);
        }
        return null;
    }
}
