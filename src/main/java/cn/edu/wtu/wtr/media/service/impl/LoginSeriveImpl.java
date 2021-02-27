package cn.edu.wtu.wtr.media.service.impl;

import cn.edu.wtu.wtr.media.dao.UserDao;
import cn.edu.wtu.wtr.media.object.User;
import cn.edu.wtu.wtr.media.object.UserExample;
import cn.edu.wtu.wtr.media.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginSeriveImpl implements ILoginService {
    @Autowired
    private UserDao dao;

    @Override
    public User login(String username,String password){
        UserExample userExample = new UserExample();
        //创建条件
        userExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        //根据条件在数据库中查询
        List<User> users = dao.selectByExample(userExample);
        //判断
        if( users == null || users.size()< 1)
            return null;
        return users.get(0);
    }
}
