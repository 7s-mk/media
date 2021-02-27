package cn.edu.wtu.wtr.media.Controller;

import cn.edu.wtu.wtr.media.dao.DynamicDao;
import cn.edu.wtu.wtr.media.object.Dynamic;
import cn.edu.wtu.wtr.media.service.IDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/content")
public class DynamicsControl {
    @Autowired
    private IDynamicService service;

    @Autowired
    private DynamicDao dao;


    @GetMapping
    public String getAll(String key, Integer size, Integer page, Model model) {
        List<Dynamic> dynamics = service.getPage(key, page, size);
        long count = service.getCount(key);
        if (page == null || page < 0)
            page = 1;
        if (size == null || size < 0)
            size = 20;
        int pageSum = (int) (count % size == 0 ? count / size : (count / size + 1));

        model.addAttribute("pageCount", pageSum);
        model.addAttribute("page", page);
        model.addAttribute("count", count);
        model.addAttribute("content", dynamics);
        model.addAttribute("key", key);
        return "content";
    }

    @GetMapping("/add")
    public String addView(Model model) {
        model.addAttribute("msg", "");
        model.addAttribute("content", new Dynamic());
        model.addAttribute("code", "");
        return "add";
    }

    @PostMapping
    public String add(Dynamic contents, Model model, String setCreateTime) {
        if (setCreateTime != null && !setCreateTime.isEmpty()) {
            contents.setCreateTime(LocalDateTime.parse(setCreateTime));
        } else {
            contents.setCreateTime(LocalDateTime.now());
        }
        if (service.add(contents)) {
            model.addAttribute("msg", "添加失败");
            model.addAttribute("content", new Dynamic());
            model.addAttribute("code", "<script type=\"text/javascript\">\n" +
                    "    alert(\"添加成功！\");\n" +
                    "    location.href=\"/content\";\n" +
                    "</script>");
            return "add";
        }
        model.addAttribute("msg", "添加失败");
        model.addAttribute("content", contents);
        model.addAttribute("code", "<script type=\"text/javascript\">\n" +
                "    alert(\"添加失败！\");\n" +
                "</script>");
        return "add";
    }
}
