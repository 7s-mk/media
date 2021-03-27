package cn.edu.wtu.wtr.media.controller;

import cn.edu.wtu.wtr.media.object.Office;
import cn.edu.wtu.wtr.media.object.wtrsystem.WTRRegisterManage;
import cn.edu.wtu.wtr.media.service.IRegisterManagerService;
import cn.edu.wtu.wtr.media.util.ErrorTools;
import cn.edu.wtu.wtr.media.util.HttpContext;
import cn.edu.wtu.wtr.media.util.PopUps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-16-16:25
 * @since 2021-03-16-16:25
 */
@Controller
@RequestMapping("wtr")
public class WTRSystemControl {
    @Autowired
    IRegisterManagerService service;

    @GetMapping("/register")
    public String view(Model model) {
        if (!HttpContext.checkOffice(Office.部长))
            return PopUps.unOffice(model, Office.部长);
        try {
            WTRRegisterManage rc = service.getRC();
            model.addAttribute("rc", rc);
            return "user_rc_m";
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorTools.error(model, e, "出现错误！\\n");
        }
    }

    @GetMapping("/register/add")
    public String add(Model model) {
        if (!HttpContext.checkOffice(Office.部长))
            return PopUps.unOffice(model, Office.部长);
        return "user_rc_post";
    }

    @PostMapping("/register/add")
    public String addPost(WTRRegisterManage.InvitationCode code, Model model) {
        if (!HttpContext.checkOffice(Office.部长))
            return PopUps.unOffice(model, Office.部长);
        try {
            if (service.setRC(code))
                return PopUps.info(model, "添加成功", "/wtr/register");
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorTools.error(model, e, "添加失败！");
        }
        return PopUps.info(model, "添加失败！");
    }

    @GetMapping("/register/del/{code}")
    public String del(@PathVariable String code, Model model) {
        if (!HttpContext.checkOffice(Office.部长))
            return PopUps.unOffice(model, Office.部长);
        try {
            if (service.remRC(code))
                return PopUps.info(model, "删除成功！");
            else
                return PopUps.info(model, "删除失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorTools.error(model, e, "删除失败！");
        }
    }

    @GetMapping("/register/open")
    public String setOpen(boolean open, Model model) {
        if (!HttpContext.checkOffice(Office.部长))
            return PopUps.unOffice(model, Office.部长);
        try {
            if (service.setISR(open))
                return PopUps.info(model, "修改成功！已" + (open ? "开放" : "关闭") + "注册");
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorTools.error(model, e, "修改失败！");
        }
        return PopUps.info(model, "修改失败！");
    }
}
