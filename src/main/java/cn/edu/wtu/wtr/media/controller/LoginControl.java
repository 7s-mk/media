package cn.edu.wtu.wtr.media.controller;

import cn.edu.wtu.wtr.media.common.MessageException;
import cn.edu.wtu.wtr.media.object.Office;
import cn.edu.wtu.wtr.media.object.User;
import cn.edu.wtu.wtr.media.service.ILoginService;
import cn.edu.wtu.wtr.media.util.ErrorTools;
import cn.edu.wtu.wtr.media.util.HttpContext;
import cn.edu.wtu.wtr.media.util.PopUps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginControl {
    @Autowired
    private ILoginService service;

    @GetMapping
    public String loginView() {
        return "redirect: /login.html";
    }

    @PostMapping
    public String login(String username, String password, Model model) {
        User login = service.login(username, password);
        if (login == null) {
            model.addAttribute("username", username);
            model.addAttribute("password", password);
            return "login"; //返回登录界面
        }
        model.addAttribute("user", login);
        // 保存登录后的用户
        HttpContext.setUser(login);
        // 设置超时时间
        HttpServletRequest request = HttpContext.getRequest();
        if (request != null)
            request.getSession().setMaxInactiveInterval(24 * 60 * 60);
        //跳转manage页面
        return "redirect:/login/manage";
    }

    // 退出登录
    @GetMapping("/out")
    public String logout(Model model) {
        HttpContext.logout();
        return PopUps.info(model, "退出成功", "/");
    }

    @GetMapping("/manage")
    public String manage(Model model) {
        if (!HttpContext.checkLogin())
            return PopUps.unLogin(model);
        model.addAttribute("user", HttpContext.getUser());
        return "manage";
    }

    @GetMapping("/my")
    public String personal(Model model) {
        if (!HttpContext.checkOffice(Office.测试))
            return PopUps.unLogin(model);
        model.addAttribute("user", HttpContext.getUser());
        return "personal";
    }

    @GetMapping("/register")
    public String registerView(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("code", "");
        return "user_register";
    }

    @PostMapping("/register")
    public String register(Model model, User user, String code) {
        try {
            User register = service.register(user, code);
            if (register == null)
                throw new MessageException("注册失败");
            return PopUps.info(model, "注册成功", "/");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("code", PopUps.popCode(ErrorTools.errorMsg(e, "注册失败！")));
            model.addAttribute("user", user);
            return "user_register";
        }
    }
}
