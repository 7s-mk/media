package cn.edu.wtu.wtr.media.Controller;

import cn.edu.wtu.wtr.media.object.User;
import cn.edu.wtu.wtr.media.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginControl {
    @Autowired
    private ILoginService service;


    @PostMapping
    public String login(String username, String password, Model model){
        User login = service.login(username, password);
        if(login == null){
            model.addAttribute("username",username);
            model.addAttribute("password",password);
            return "login"; //返回登录界面
        }
        model.addAttribute("user",login);
        return "manage"; //跳转manage页面
    }

}
