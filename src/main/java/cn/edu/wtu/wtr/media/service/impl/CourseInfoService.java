package cn.edu.wtu.wtr.media.service.impl;

import cn.edu.wtu.wtr.media.dao.CourseinfoDao;
import cn.edu.wtu.wtr.media.object.CourseVo;
import cn.edu.wtu.wtr.media.object.Courseinfo;
import cn.edu.wtu.wtr.media.object.CourseinfoExample;
import cn.edu.wtu.wtr.media.object.User;
import cn.edu.wtu.wtr.media.service.ICourseInfoService;
import cn.edu.wtu.wtr.media.service.ICourseService;
import cn.edu.wtu.wtr.media.util.HttpContext;
import cn.edu.wtu.wtr.media.util.education.login.JWTools;
import cn.edu.wtu.wtr.media.util.education.model.Course;
import cn.edu.wtu.wtr.media.util.education.model.CourseStu;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-22:35
 * @since 2021-03-15-22:35
 */
@Service
public class CourseInfoService implements ICourseInfoService {
    @Autowired
    CourseinfoDao dao;
    @Autowired
    ICourseService service;

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
    @Override
    public CourseVo load(String sid, String pwd, String code, String xn, String xq) {
        List<Course> courses = service.loadCourse(sid, pwd, code, xn, xq);
        return handleCourses(sid, xq, xn, courses);
    }

    /**
     * 手动加载
     *
     * @param json json
     * @return vo
     */
    @Override
    public CourseVo load(String json) {
        // 解析json
        List<Course> courses = JWTools.analyzeCourse(json);
        CourseStu stu = JWTools.analyzeCourseStu(json);
        if (stu != null)
            return handleCourses(stu.getSid(), stu.getYears(), stu.getTerm(), courses);
        throw new RuntimeException("手动加载失败");
    }

    /**
     * 获取
     *
     * @param sid 用户id
     * @return vo
     */
    @Override
    public CourseVo get(String sid) {
        CourseinfoExample example = new CourseinfoExample();
        example.createCriteria().andSidEqualTo(sid);
        List<Courseinfo> courseinfos = dao.selectByExample(example);
        return CourseVo.build(courseinfos != null && courseinfos.size() > 0 ? courseinfos.get(0) : null);
    }

    /**
     * 根据ID获取
     *
     * @param id id
     * @return vo
     */
    @Override
    public CourseVo getByID(int id) {
        CourseinfoExample example = new CourseinfoExample();
        example.createCriteria().andIdEqualTo(id);
        example.setOrderByClause("dateTime desc");
        List<Courseinfo> courseinfos = dao.selectByExample(example);
        return CourseVo.build(courseinfos != null && courseinfos.size() > 0 ? courseinfos.get(0) : null);
    }

    @Override
    public CourseVo getByID(int id, String year, String term) {
        if (year == null || year.isEmpty() || term == null || term.isEmpty())
            return getByID(id);
        List<Courseinfo> courseinfos = dao.selectByExample(getExample(id, year, term));
        return CourseVo.build(courseinfos != null && courseinfos.size() > 0 ? courseinfos.get(0) : null);
    }


    /**
     * 全部
     *
     * @return 全部
     */
    @Override
    public List<CourseVo> list() {
        return CourseVo.build(dao.selectByExample(null));
    }

    /**
     * 获取某个学期的全部
     *
     * @param year 学年
     * @param term 学期
     * @return list
     */
    @Override
    public List<CourseVo> list(String year, String term) {
        CourseinfoExample example = new CourseinfoExample();
        example.createCriteria().andYearEqualTo(year).andTermEqualTo(term);
        return CourseVo.build(dao.selectByExample(example));
    }


    /**
     * 获取某个学期的区别
     *
     * @param year 年
     * @param term 学
     * @param name 模糊姓名
     * @return list
     */
    @Override
    public List<CourseVo> list(String year, String term, String name, Integer size, Integer page) {
        if (name == null || name.isEmpty())
            return list(year, term);
        CourseinfoExample example = new CourseinfoExample();
        example.createCriteria().andYearEqualTo(year).andTermEqualTo(term).andNameLike("%" + name + "%");
        size = size == null || size < 10 ? 20 : size;
        page = page == null || page < 1 ? 1 : page;
        example.setLimit(size);
        example.setOffset((long) size * (page - 1));
        return CourseVo.build(dao.selectByExample(example));
    }

    /**
     * 删除指定课表
     *
     * @param id 课表
     * @return ok？
     */
    @Override
    public boolean delCourse(int id) {
        return dao.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 获取总数
     */
    @Override
    public long count(String year, String term, String name) {
        CourseinfoExample example = new CourseinfoExample();
        name = name == null ? "" : name;
        example.createCriteria().andYearEqualTo(year).andTermEqualTo(term).andNameLike("%" + name + "%");
        return dao.countByExample(example);
    }

    /*  封装 */

    /**
     * 获取条件
     *
     * @param id   用户id
     * @param year 学年
     * @param term 学期
     * @return 条件
     */
    private CourseinfoExample getExample(int id, String year, String term) {
        CourseinfoExample example = new CourseinfoExample();
        example.createCriteria().andIdEqualTo(id).andYearEqualTo(year).andTermEqualTo(term);
        return example;
    }

    /**
     * 处理课表并写入
     *
     * @param sid     学会
     * @param xn      学年
     * @param xq      学期
     * @param courses 课程表
     * @return vo
     */
    private CourseVo handleCourses(String sid, String xn, String xq, List<Course> courses) {
        User user = HttpContext.getUser();
        Courseinfo courseinfo = null;
        if (user != null) {
            CourseinfoExample example = getExample(user.getId(), xn, xq);
            courseinfo = new Courseinfo(user.getId(), sid, user.getName(), xn, xq,
                    JSONArray.toJSON(courses).toString(), LocalDateTime.now());
            List<Courseinfo> courseInfos = dao.selectByExample(example);
            if (courseInfos != null && courseInfos.size() > 0)
                dao.updateByExampleSelective(courseinfo, example);
            else
                dao.insert(courseinfo);
        }
        return CourseVo.build(courseinfo);
    }
}
