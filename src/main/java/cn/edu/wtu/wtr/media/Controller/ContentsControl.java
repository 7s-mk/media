package cn.edu.wtu.wtr.media.Controller;

import cn.edu.wtu.wtr.media.object.Contents;
import cn.edu.wtu.wtr.media.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/content")
public class ContentsControl {
    @Autowired
    private IContentService service;

    @GetMapping
    public String getAll(String key, Model model){
        List<Contents> all = service.getAll(key);
        model.addAttribute("content",all);
        model.addAttribute("key",key);
        return "content";
    }

    @GetMapping("/add")
    public String addView(Model model){
        model.addAttribute("msg","");
        model.addAttribute("content",new Contents());
        model.addAttribute("code","");
        return "add";
    }

    @PostMapping
    public String add(Contents contents,Model model){
        if(service.add(contents)) {
            model.addAttribute("msg", "添加失败");
            model.addAttribute("content", new Contents());
            model.addAttribute("code", "<script type=\"text/javascript\">\n" +
                    "    alert(\"添加成功！\");\n" +
                    "    location.href=\"/content\";\n" +
                    "</script>");
            return "add";
        }
        model.addAttribute("msg","添加失败");
        model.addAttribute("content", contents);
        model.addAttribute("code","<script type=\"text/javascript\">\n" +
                "    alert(\"添加失败！\");\n" +
                "</script>");
        return "add";
    }
}
