package cn.edu.wtu.wtr.media.service;

import cn.edu.wtu.wtr.media.object.CourseVo;
import cn.edu.wtu.wtr.media.object.User;

import java.util.List;

/**
 * 描述： 课表接口
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-22:26
 * @since 2021-03-15-22:26
 */
public interface ICourseInfoService {

    /**
     * 加载
     *
     * @param sid  学号
     * @param pwd  密码
     * @param code 验证码
     * @param xn   学年
     * @param xq   学期
     * @return vo
     */
    CourseVo load(String sid, String pwd, String code, String xn, String xq);

    /**
     * 手动加载
     *
     * @param json json
     * @return vo
     */
    CourseVo load(String json);

    /**
     * 获取
     *
     * @param sid 用户id
     * @return vo
     */
    CourseVo get(String sid);

    /**
     * 根据ID获取
     *
     * @param id id
     * @return vo
     */
    CourseVo getByID(int id);

    /**
     * 获取指定学期的
     *
     * @param id   学号
     * @param year 学年
     * @param term 学期
     * @return vo
     */
    CourseVo getByID(int id, String year, String term);

    /**
     * 全部
     *
     * @return 全部
     */
    List<CourseVo> list();


    /**
     * 获取某个学期的全部
     *
     * @param year 学年
     * @param term 学期
     * @return list
     */
    List<CourseVo> list(String year, String term, String depart);

    /**
     * 获取某个学期的区别
     *
     * @param year   年
     * @param term   学
     * @param depart 部门
     * @param name   模糊姓名
     * @return list
     */
    List<CourseVo> list(String year, String term, String depart, String name, Integer size, Integer page);

    /**
     * 删除指定课表
     *
     * @param id 课表
     * @return ok？
     */
    boolean delCourse(int id);

    /**
     * 获取总数
     */
    long count(String year, String term, String depart, String name);


    /**
     * 获取未录入课表的人
     *
     * @param year   学年
     * @param term   学期
     * @param depart 部门
     * @return user
     */
    List<User> getNotCourseUsers(String year, String term, String depart);

}
