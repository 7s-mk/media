package cn.edu.wtu.wtr.media.Controller;

import cn.edu.wtu.wtr.media.object.Office;
import cn.edu.wtu.wtr.media.object.User;
import cn.edu.wtu.wtr.media.service.impl.UserService;
import cn.edu.wtu.wtr.media.util.HttpContext;
import cn.edu.wtu.wtr.media.util.PopUps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 描述： 用户接口
 *
 * @author 郭沁雅
 * @version 1.0  2021-02-28-14:17
 * @since 2021-02-28-14:17
 */
@RequestMapping("/user")
@Controller
public class UserControl {
    @Autowired
    private UserService service;

    /**
     * 获取用户信息
     *
     * @param id    id
     * @param model model
     * @return 信息
     */
    @GetMapping("/info/{id}")
    public String getUserInfo(@PathVariable String id, Model model) {
        if (!HttpContext.checkLogin())
            return PopUps.unLogin(model);
        try {
            User user = service.getUser(Integer.parseInt(id));
            user.setPassword("****************");
            model.addAttribute("user", user);
            return "user_info";
        } catch (Exception e) {
            return PopUps.info(model, "获取信息异常或该用户不存在！", "/");
        }
    }

    @GetMapping()
    public String getAllUser(Model model, String key) {
        if (!HttpContext.checkOffice(Office.部长))
            return PopUps.unOffice(model, Office.部长);
        List<User> user = service.getUser(key);
        model.addAttribute("count", user.size());
        model.addAttribute("content", user);
        model.addAttribute("key", key);
        return "user";
    }

    @GetMapping("/remove")
    public String remove(Model model, int id) {
        if (!HttpContext.checkOffice(Office.台长))
            return PopUps.unOffice(model, Office.台长);
        return PopUps.info(model, service.remove(id) ? "删除成功" : "删除失败！");
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("title", "添加用户");
        model.addAttribute("action", "add");
        model.addAttribute("msg", "注意！职务至少是部长才有权添加！");
        model.addAttribute("user", new User());
        model.addAttribute("code", "");
        return "user_post";
    }

    @PostMapping("/add")
    public String addDo(Model model, User user) {
        // 如果权限不足
        if (!HttpContext.checkOffice(Office.部长))
            return PopUps.unOffice(model, Office.部长);
        // 添加成功
        if (service.addUser(user)) {
            return PopUps.info(model, "添加成功", "/user");
        }
        // 添加失败
        model.addAttribute("title", "添加用户");
        model.addAttribute("action", "add");
        model.addAttribute("msg", "添加失败!添加的职务必须低于自己当前职务");
        model.addAttribute("user", user);
        model.addAttribute("code", PopUps.popCode("添加失败！"));
        return "user_post";
    }

    @GetMapping("/modify")
    public String modify(Model model, int id) {
        model.addAttribute("title", "修改用户");
        model.addAttribute("action", "modify?id=" + id);
        model.addAttribute("msg", "注意！职务至少是部长才有权修改！");
        model.addAttribute("user", service.getUser(id));
        model.addAttribute("code", "");
        return "user_post";
    }

    @PostMapping("/modify")
    public String modifyDo(Model model, User user, int id) {
        if (!HttpContext.checkOffice(Office.部长))
            return PopUps.unOffice(model, Office.部长);
        if (service.modify(user)) {
            return PopUps.info(model, "修改成功！", "/user");
        }
        model.addAttribute("title", "修改用户");
        model.addAttribute("action", "modify?id=" + id);
        model.addAttribute("msg", "修改失败！职务不能高于登录用户的职务且用户名不可重复！");
        model.addAttribute("user", user);
        model.addAttribute("code", PopUps.popCode("修改失败！"));
        return "user_post";
    }


}
