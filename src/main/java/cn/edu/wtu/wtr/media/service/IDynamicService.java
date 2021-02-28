package cn.edu.wtu.wtr.media.service;

import cn.edu.wtu.wtr.media.object.Dynamic;

import java.util.List;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-02-27-20:41
 * @since 2021-02-27-20:41
 */
public interface IDynamicService {
    /**
     * 获取当前搜索的总条数
     *
     * @param key 关键字
     * @return 条数
     */
    long getCount(String key);

    /**
     * 分页获取动态
     *
     * @param key  关键字
     * @param page 页数
     * @param size 每页对象 默认20
     * @return 动态集合
     */
    List<Dynamic> getPage(String key, Integer page, Integer size);

    /**
     * 根据id获取
     *
     * @param id id
     * @return dynamic
     */
    Dynamic getDynamic(int id);

    /**
     * 添加一条动态
     *
     * @param dynamic 动态
     * @return 是否添加成功
     */
    boolean add(Dynamic dynamic);

    /**
     * 修改数据
     *
     * @param dynamic 动态
     * @return 修改成功
     */
    boolean modify(Dynamic dynamic);

    /**
     * 删除一条数据
     *
     * @param id id
     * @return 是否删除成功
     */
    boolean remove(int id);
}
