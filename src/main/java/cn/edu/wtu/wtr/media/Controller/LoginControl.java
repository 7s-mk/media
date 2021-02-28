package cn.edu.wtu.wtr.media.Controller;

import cn.edu.wtu.wtr.media.object.User;
import cn.edu.wtu.wtr.media.service.ILoginService;
import cn.edu.wtu.wtr.media.util.HttpContext;
import cn.edu.wtu.wtr.media.util.PopUps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginControl {
    @Autowired
    private ILoginService service;


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
        return "manage"; //跳转manage页面
    }

    // 退出登录
    @GetMapping("/out")
    public String logout(Model model) {
        HttpContext.logout();
        return PopUps.info(model, "退出成功", "/");
    }

}
