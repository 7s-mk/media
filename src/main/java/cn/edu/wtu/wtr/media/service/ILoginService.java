package cn.edu.wtu.wtr.media.service;

import cn.edu.wtu.wtr.media.object.User;

public interface ILoginService {
    User login(String name, String password);

    /**
     * 注册
     *
     * @param user 信息
     * @param code 邀请码
     * @return user
     */
    User register(User user, String code);
}
