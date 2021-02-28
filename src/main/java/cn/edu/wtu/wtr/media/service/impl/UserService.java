package cn.edu.wtu.wtr.media.service.impl;

import cn.edu.wtu.wtr.media.dao.UserDao;
import cn.edu.wtu.wtr.media.object.User;
import cn.edu.wtu.wtr.media.object.UserExample;
import cn.edu.wtu.wtr.media.service.IUserService;
import cn.edu.wtu.wtr.media.util.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：用户管理实现类
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-02-28-14:13
 * @since 2021-02-28-14:13
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao dao;

    /**
     * 添加用户
     *
     * @param user 添加用户
     * @return 添加后的用户
     */
    @Override
    public boolean addUser(User user) {
        if (!check(user) || !HttpContext.checkOffice(user.office()))
            return false;
        return dao.insertSelective(user) == 1;
    }

    /**
     * 修改用户数据
     *
     * @param user 用户
     * @return 是否成功
     */
    @Override
    public boolean modify(User user) {
        if (!check(user) || user.getId() == null)
            return false;
        return dao.updateByPrimaryKey(user) == 1;
    }

    /**
     * 删除用户
     *
     * @param id id
     * @return 是否成功
     */
    @Override
    public boolean remove(int id) {
        return dao.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 获取全部用户
     *
     * @return 全部
     */
    @Override
    public List<User> getUser(String key) {
        UserExample example = null;
        if (key != null) {
            example = new UserExample();
            example.createCriteria().andNameLike("%" + key + "%");
        }
        List<User> users = dao.selectByExample(example);
        if (users == null)
            return null;
        // 只显示权限低于自己以及和自己相同的的
        return users.stream().filter(user -> HttpContext.checkOffice(user.office())).collect(Collectors.toList());
    }


    /**
     * 判断用户是否正常
     *
     * @param user 用户
     * @return 是否
     */
    private boolean check(User user) {
        return user != null && user.getName() != null && user.getPassword() != null;
    }
}
