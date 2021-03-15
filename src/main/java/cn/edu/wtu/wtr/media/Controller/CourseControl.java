package cn.edu.wtu.wtr.media.Controller;

import cn.edu.wtu.wtr.media.service.ICourseService;
import cn.edu.wtu.wtr.media.util.education.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private ICourseService service;

    @ResponseBody
    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] open() {
        return service.checkImage();
    }

    @ResponseBody
    @GetMapping("/load")
    public List<Course> geCourse(String sid, String pwd, String code, String xn, String xq) {
        return service.loadCourse(sid, pwd, code, xn, xq);
    }

}
