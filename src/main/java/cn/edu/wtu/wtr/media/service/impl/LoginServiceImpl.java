package cn.edu.wtu.wtr.media.service.impl;

import cn.edu.wtu.wtr.media.dao.UserDao;
import cn.edu.wtu.wtr.media.object.Office;
import cn.edu.wtu.wtr.media.object.User;
import cn.edu.wtu.wtr.media.object.UserExample;
import cn.edu.wtu.wtr.media.object.Wtr;
import cn.edu.wtu.wtr.media.object.wtrsystem.WTRRegisterManage;
import cn.edu.wtu.wtr.media.service.ILoginService;
import cn.edu.wtu.wtr.media.service.IRegisterManagerService;
import cn.edu.wtu.wtr.media.util.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class LoginServiceImpl implements ILoginService, IRegisterManagerService {
    @Autowired
    private UserDao dao;
    @Autowired
    private WTRService service;


    @Override
    public User login(String username, String password) {
        UserExample userExample = new UserExample();
        //创建条件
        userExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        //根据条件在数据库中查询
        List<User> users = dao.selectByExample(userExample);
        //判断
        if (users == null || users.size() < 1)
            return null;
        return users.get(0);
    }

    @Override
    public User register(User user, String code) {
        Wtr wtr = service.getWtr(WTRRegisterManage.KEY);
        WTRRegisterManage build = WTRRegisterManage.build(wtr);
        if (build == null || !build.isOpen()) {
            throw new RuntimeException("未开放注册!");
        }
        WTRRegisterManage.InvitationCode invitationCode = build.get(code);
        // 有设置 或台委会则不修改
        if (invitationCode.getDepartment() != null && !invitationCode.getDepartment().isEmpty()
                && !"主席团".equals(invitationCode.getDepartment()))
            user.setDepartment(invitationCode.getDepartment());
        user.setOffice(Office.干事.toString());
        if (dao.insert(user) == 1)
            return user;
        throw new RuntimeException("用户名重复或其他异常！");
    }

    /**
     * 设置邀请码
     *
     * @param code code
     * @return ok?
     */
    @Override
    public boolean setRC(WTRRegisterManage.InvitationCode code) {
        WTRRegisterManage wtr = WTRRegisterManage.build(service.getWtr(WTRRegisterManage.KEY));
        wtr = wtr == null ? new WTRRegisterManage() : wtr;
        code.setName(Objects.requireNonNull(HttpContext.getUser()).getName());
        code.setDateTime(LocalDateTime.now());
        wtr.add(code);
        return service.setWtr(wtr.toWtr());
    }

    @Override
    public boolean remRC(String code) {
        WTRRegisterManage wtr = WTRRegisterManage.build(service.getWtr(WTRRegisterManage.KEY));
        if (wtr == null)
            return true;
        wtr.remove(code);
        return service.setWtr(wtr.toWtr());
    }

    /**
     * 设置是否可以注册
     *
     * @param isR 是否可
     * @return ok?
     */
    @Override
    public boolean setISR(boolean isR) {
        WTRRegisterManage wtr = WTRRegisterManage.build(service.getWtr(WTRRegisterManage.KEY));
        wtr = wtr == null ? new WTRRegisterManage() : wtr;
        wtr.setOpen(isR);
        return service.setWtr(wtr.toWtr());
    }

    @Override
    public WTRRegisterManage getRC() {
        WTRRegisterManage wtr = WTRRegisterManage.build(service.getWtr(WTRRegisterManage.KEY));
        wtr = wtr == null ? new WTRRegisterManage() : wtr;
        return wtr;
    }
}
