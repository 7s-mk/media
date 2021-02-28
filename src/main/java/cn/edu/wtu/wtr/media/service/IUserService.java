package cn.edu.wtu.wtr.media.service;

import cn.edu.wtu.wtr.media.object.User;

import java.util.List;

/**
 * 描述： 用户接口
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-02-28-14:10
 * @since 2021-02-28-14:10
 */
public interface IUserService {
    /**
     * 添加用户
     *
     * @param user 添加用户
     * @return 添加后的用户
     */
    boolean addUser(User user);

    /**
     * 修改用户数据
     *
     * @param user 用户
     * @return 是否成功
     */
    boolean modify(User user);

    /**
     * 删除用户
     *
     * @param id id
     * @return 是否成功
     */
    boolean remove(int id);

    /**
     * 获取全部用户
     *
     * @return 全部
     */
    List<User> getUser(String key);

    /**
     * 根据id获取
     * @param id id
     * @return user
     */
    User getUser(int id);

}
