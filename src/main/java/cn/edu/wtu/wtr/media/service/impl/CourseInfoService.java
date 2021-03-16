package cn.edu.wtu.wtr.media.service.impl;

import cn.edu.wtu.wtr.media.dao.CourseinfoDao;
import cn.edu.wtu.wtr.media.object.CourseVo;
import cn.edu.wtu.wtr.media.object.Courseinfo;
import cn.edu.wtu.wtr.media.object.CourseinfoExample;
import cn.edu.wtu.wtr.media.object.User;
import cn.edu.wtu.wtr.media.service.ICourseInfoService;
import cn.edu.wtu.wtr.media.service.ICourseService;
import cn.edu.wtu.wtr.media.util.HttpContext;
import cn.edu.wtu.wtr.media.util.education.model.Course;
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
        User user = HttpContext.getUser();
        Courseinfo courseinfo = null;
        if (user != null) {
            CourseinfoExample example = getExample(user.getId(), xn, xq);
            courseinfo = new Courseinfo(user.getId(), sid, user.getName(), xn, xq,
                    JSONArray.toJSON(courses).toString(), LocalDateTime.now());
            List<Courseinfo> courseinfos = dao.selectByExample(example);
            if (courseinfos != null && courseinfos.size() > 0)
                dao.updateByExampleSelective(courseinfo, example);
            else
                dao.insert(courseinfo);
        }
        return CourseVo.build(courseinfo);
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
}
