package cn.edu.wtu.wtr.media.Controller;

import cn.edu.wtu.wtr.media.object.CourseVo;
import cn.edu.wtu.wtr.media.object.WeekImage;
import cn.edu.wtu.wtr.media.service.ICourseInfoService;
import cn.edu.wtu.wtr.media.service.ICourseService;
import cn.edu.wtu.wtr.media.util.HttpContext;
import cn.edu.wtu.wtr.media.util.PopUps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @GetMapping("/load")
    public String load() {
        return "course_load";
    }

    @GetMapping()
    public String course(Model model, String year, String term) {
        if (!HttpContext.checkLogin())
            return PopUps.unLogin(model);
        try {
            CourseVo vo = service.getByID(HttpContext.getUser().getId(), year, term);
            model.addAttribute("courseInfo", toWeekImage(vo));
            return "course";
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.toString());
            return PopUps.info(model, "加载失败,无当前学期课表!请添加载课表\\n" +
                    (e.getMessage() == null ? "" : e.getMessage()), "/course/load");
        }
    }

    @ResponseBody
    @GetMapping(value = "/open", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] open() {
        return serviceOpen.checkImage();
    }

    @PostMapping("/load")
    public String geCourse(String sid, String pwd, String code, String xn, String xq, Model model) {
        if (!HttpContext.checkLogin())
            return PopUps.unLogin(model);
        try {
            CourseVo load = service.load(sid, pwd, code, xn, xq);
            model.addAttribute("courseInfo", toWeekImage(load));
            return "course";
        } catch (Exception e) {
            System.err.println(e.toString());
            return PopUps.info(model, "加载失败!\\n" + e.getMessage());
        }
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
