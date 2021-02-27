package cn.edu.wtu.wtr.media.service;

import cn.edu.wtu.wtr.media.object.User;

public interface ILoginService {
    User login(String name, String password);
}
