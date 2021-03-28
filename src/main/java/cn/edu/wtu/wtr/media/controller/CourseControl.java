package cn.edu.wtu.wtr.media.controller;

import cn.edu.wtu.wtr.media.object.CourseVo;
import cn.edu.wtu.wtr.media.object.Office;
import cn.edu.wtu.wtr.media.object.WeekImage;
import cn.edu.wtu.wtr.media.object.empty.EmptyUserTable;
import cn.edu.wtu.wtr.media.service.ICourseInfoService;
import cn.edu.wtu.wtr.media.service.ICourseService;
import cn.edu.wtu.wtr.media.util.CommonUtils;
import cn.edu.wtu.wtr.media.util.ErrorTools;
import cn.edu.wtu.wtr.media.util.HttpContext;
import cn.edu.wtu.wtr.media.util.PopUps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-21:47
 * @since 2021-03-15-21:47
 */
@Controller
@RequestMapping("/course")
public class CourseControl {

    @Autowired
    private ICourseService serviceOpen;
    @Autowired
    private ICourseInfoService service;

    /**
     * 查看课表
     */
    @GetMapping("/info/{id}")
    public String courseInfo(Model model, String year, String term, @PathVariable String id) {
        if (!HttpContext.checkLogin())
            return PopUps.unLogin(model);
        try {
            CourseVo vo = service.getByID(Integer.parseInt(id), year, term);
            model.addAttribute("courseInfo", toWeekImage(vo));
            return "course";
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorTools.error(model, e, "加载失败,无当前学期课表!请添加载课表\\n", "/course/load");
        }
    }

    @GetMapping()
    public String course(Model model, String year, String term) {
        if (!HttpContext.checkLogin())
            return PopUps.unLogin(model);
        return courseInfo(model, year, term, HttpContext.getUser().getId().toString());
//        try {
//            CourseVo vo = service.getByID(HttpContext.getUser().getId(), year, term);
//            model.addAttribute("courseInfo", toWeekImage(vo));
//            return "course";
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println(e.toString());
//            return PopUps.info(model, "加载失败,无当前学期课表!请添加载课表\\n" +
//                    (e.getMessage() == null ? "" : e.getMessage()), "/course/load");
//        }
    }

    @GetMapping("/load")
    public String load() {
        return "course_load";
    }

    /**
     * 验证码
     *
     * @return 验证码图片
     */
    @ResponseBody
    @GetMapping(value = "/open", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] open() {
        return serviceOpen.checkImage();
    }


    /**
     * 自动加载课表
     *
     * @param sid   学号
     * @param pwd   密码
     * @param code  验证码
     * @param xn    学期
     * @param xq    学年
     * @param model model
     * @return 加载课表
     */
    @PostMapping("/load")
    public String loadCourse(String sid, String pwd, String code, String xn, String xq, Model model) {
        if (!HttpContext.checkLogin())
            return PopUps.unLogin(model);
        try {
            CourseVo load = service.load(sid, pwd, code, xn, xq);
            model.addAttribute("courseInfo", toWeekImage(load));
            return "course";
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorTools.error(model, e, "加载失败！");
        }
    }

    @GetMapping("/load/man")
    public String loadMan() {
        return "course_load_man";
    }

    /**
     * 手动加载课表
     *
     * @param json  json
     * @param model model
     * @return 课表
     */
    @PostMapping("/load/man")
    public String loadCourseMan(String json, Model model) {
        if (!HttpContext.checkLogin())
            return PopUps.unLogin(model);
        try {
            CourseVo load = service.load(json);
            model.addAttribute("courseInfo", toWeekImage(load));
            return "course";
        } catch (Exception e) {
            System.err.println(e.toString());
            return ErrorTools.error(model, e, "加载失败!");
        }
    }

    /**
     * 查看空课表
     *
     * @param year 学年
     * @param term 学期
     * @return 空课表
     */
    @GetMapping("/empty")
    public String getEmpty(String year, String term, String depart, Model model) {
        if (!HttpContext.checkLogin())
            return PopUps.unLogin(model);
        if (CommonUtils.isNullStr(year))
            year = "2021";
        if (CommonUtils.isNullStr(term))
            term = "3";
        if (CommonUtils.isNullStr(depart))
            depart = "all";

        try {
            List<CourseVo> list = service.list(year, term, depart);
            WeekImage weekImage = EmptyUserTable.build(list).toWeekView();
            weekImage.setYear(year);
            weekImage.setTerm(term);
            model.addAttribute("emptyCourse", weekImage);
            model.addAttribute("depart", depart);
            return "course_empty";
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorTools.error(model, e, "加载失败!\\n");
        }
    }


    /* 课表管理 */


    @GetMapping("/manage")
    public String courseManagePage(Model model) {
        return courseManage("all", "all", "all", "", 20, 1, model);
    }

    /**
     * 课表管理页
     *
     * @return 课表管理页
     */
    @GetMapping("/manage/{year}/{term}")
    public String courseManage(@PathVariable String term, @PathVariable String year, String depart, String key,
                               Integer size, Integer page, Model model) {
        if (!HttpContext.checkOffice(Office.部长))
            return PopUps.unOffice(model, Office.部长);
        if (year == null || year.isEmpty())
            year = "2021";
        if (term == null || term.isEmpty())
            term = "3";
        try {
            long count = service.count(year, term, depart, key);
            if (page == null || page < 0) {
                page = 1;
            }
            if (size == null || size < 0) {
                size = 20;
            }
            int pageSum = (int) (count % size == 0 ? count / size : (count / size + 1));
            List<CourseVo> list = service.list(year, term, depart, key, size, page);
            model.addAttribute("pageCount", pageSum);
            model.addAttribute("page", page);
            model.addAttribute("count", count);
            model.addAttribute("content", list);
            model.addAttribute("key", key);
            model.addAttribute("year", year);
            model.addAttribute("term", term);
            model.addAttribute("depart", depart);
            return "course_list";
        } catch (Exception e) {
            return ErrorTools.error(model, e, "加载数据失败！");
        }
    }

    /**
     * 删除
     */
    @GetMapping("/manage/delete")
    public String courseMangeDel(Integer id, Model model) {
        if (!HttpContext.checkOffice(Office.部长))
            return PopUps.unOffice(model, Office.部长);
        try {
            if (service.delCourse(id))
                return PopUps.info(model, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PopUps.info(model, "删除失败！");
    }


    /**
     * 转换韦 week image
     *
     * @param courseVo vo
     * @return image
     */
    private WeekImage toWeekImage(CourseVo courseVo) {
        WeekImage weekImage = new WeekImage();
        weekImage.setSid(courseVo.getSid());
        weekImage.setName(courseVo.getName());
        weekImage.setId(courseVo.getId());
        weekImage.setYear(courseVo.getYear());
        weekImage.setTerm(courseVo.getTerm());
        courseVo.getCourses().forEach(course -> weekImage.add(course.getDay(), course.getTime(),
                CourseVo.toView(course)));
        return weekImage;
    }

}
