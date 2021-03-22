package cn.edu.wtu.wtr.media.service;

import cn.edu.wtu.wtr.media.object.CourseVo;

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
    List<CourseVo> list(String year, String term);

}
