package cn.edu.wtu.wtr.media.Controller;

import cn.edu.wtu.wtr.media.object.Dynamic;
import cn.edu.wtu.wtr.media.object.Office;
import cn.edu.wtu.wtr.media.service.IDynamicService;
import cn.edu.wtu.wtr.media.util.HttpContext;
import cn.edu.wtu.wtr.media.util.PopUps;
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

    @GetMapping
    public String getAll(String key, Integer size, Integer page, Model model) {
        // 如果连访客权限都没有就是未登录
        if (!HttpContext.checkOffice(Office.访客))
            return PopUps.unLogin(model);
        System.out.println(HttpContext.getOffice());
        System.out.println(HttpContext.checkOffice(Office.访客));

        // 登录了才能获取
        long count = service.getCount(key);
        if (page == null || page < 0) {
            page = 1;
        }
        if (size == null || size < 0) {
            size = 20;
        }
        int pageSum = (int) (count % size == 0 ? count / size : (count / size + 1));
        List<Dynamic> dynamics = service.getPage(key, page, size);

        model.addAttribute("pageCount", pageSum);
        model.addAttribute("page", page);
        model.addAttribute("count", count);
        model.addAttribute("content", dynamics);
        model.addAttribute("key", key);
        return "content";
    }

    @GetMapping("/add")
    public String addView(Model model) {// 如果不是干事就不能添加
        model.addAttribute("title", "添加数据");
        model.addAttribute("action", "add");
        model.addAttribute("msg", "注意！职务至少是干事才有权添加！");
        model.addAttribute("content", new Dynamic());
        model.addAttribute("code", "");
        return "content_post";
    }

    @PostMapping("/add")
    public String add(Dynamic contents, Model model, String setCreateTime) {
        // 如果不是干事就不能添加
        if (!HttpContext.checkOffice(Office.干事))
            return PopUps.unOffice(model, Office.干事);

        if (setCreateTime != null && !setCreateTime.isEmpty()) {
            contents.setCreateTime(LocalDateTime.parse(setCreateTime));
        } else {
            contents.setCreateTime(LocalDateTime.now());
        }

        if (service.add(contents)) {
            // 如果添加成功
            return PopUps.info(model, "添加成功", "/content");
        }
        // 否则添加失败
        model.addAttribute("title", "添加数据");
        model.addAttribute("action", "add");
        model.addAttribute("msg", "添加失败");
        model.addAttribute("content", contents);
        model.addAttribute("code", PopUps.popCode("添加失败"));
        return "content_post";
    }

    @GetMapping("/modify")
    public String modify(Model model, int id) {
        model.addAttribute("title", "修改数据");
        model.addAttribute("action", "modify?id=" + id);
        model.addAttribute("msg", "注意！职务至少是干事才有权修改！");
        model.addAttribute("content", service.getDynamic(id));
        model.addAttribute("code", "");
        return "content_post";
    }

    @PostMapping("/modify")
    public String modifyDo(Model model, int id, Dynamic dynamic, String setCreateTime) {
        if (!HttpContext.checkOffice(Office.干事))
            return PopUps.unOffice(model, Office.干事);
        if (setCreateTime != null && !setCreateTime.isEmpty()) {
            dynamic.setCreateTime(LocalDateTime.parse(setCreateTime));
        }
        if (service.modify(dynamic)) {
            // 如果成功
            return PopUps.info(model, "修改成功！", "/content");
        }
        // 如果失败
        model.addAttribute("title", "修改数据");
        model.addAttribute("action", "modify?id=" + id);
        model.addAttribute("msg", "修改失败！");
        model.addAttribute("content", dynamic);
        model.addAttribute("code", PopUps.popCode("修改失败！"));
        return "content_post";
    }

    @GetMapping("/remove")
    public String remove(int id, Model model) {
        if (!HttpContext.checkOffice(Office.小组长))
            return PopUps.unOffice(model, Office.小组长);

        boolean remove = service.remove(id);
        // 修改为封装的方法
        return PopUps.info(model, remove ? "删除成功！" : "删除失败");
    }
}
