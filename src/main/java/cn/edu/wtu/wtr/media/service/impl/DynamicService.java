package cn.edu.wtu.wtr.media.service.impl;

import cn.edu.wtu.wtr.media.dao.DynamicDao;
import cn.edu.wtu.wtr.media.object.Dynamic;
import cn.edu.wtu.wtr.media.object.DynamicExample;
import cn.edu.wtu.wtr.media.service.IDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-02-27-20:44
 * @since 2021-02-27-20:44
 */
@Service
public class DynamicService implements IDynamicService {
    /**
     * 数据库连接对象
     */
    @Autowired
    private DynamicDao dao;

    @Override
    public long getCount(String key) {
        DynamicExample example = new DynamicExample();
        // 如果 key不为空 就设置一下 搜索条件
        if (key != null) {
            example.createCriteria().andValueLike("%" + key + "%");
        }
        return dao.countByExample(example);
    }

    @Override
    public List<Dynamic> getPage(String key, Integer page, Integer size) {
        DynamicExample example = new DynamicExample();
        // 不能为负数
        if (size == null || size < 0)
            size = 20;
        if (page == null || page < 0)
            page = 1;
        // 如果 key不为空 就设置一下 搜索条件
        if (key != null) {
            example.createCriteria().andValueLike("%" + key + "%");
        } else {
            example.createCriteria().andValueLike("%%");
        }
        // limit 每页大小
        example.setLimit(size);
        // offset 20
        example.setOffset(((long) size * (page - 1)));
        return dao.selectByExample(example);
    }

    @Override
    public boolean add(Dynamic dynamic) {
        if (dynamic.getCreateTime() == null) {
            dynamic.setCreateTime(LocalDateTime.now());
        }
        return dao.insertSelective(dynamic) == 1;
    }

    @Override
    public boolean remove(int id) {
        return dao.deleteByPrimaryKey(id) == 1;
    }
}
