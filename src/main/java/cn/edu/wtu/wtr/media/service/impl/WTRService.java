package cn.edu.wtu.wtr.media.service.impl;

import cn.edu.wtu.wtr.media.dao.WtrDao;
import cn.edu.wtu.wtr.media.object.Wtr;
import cn.edu.wtu.wtr.media.object.WtrExample;
import cn.edu.wtu.wtr.media.service.IWTRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-16-16:06
 * @since 2021-03-16-16:06
 */
@Service
public class WTRService implements IWTRService {
    @Autowired
    private WtrDao dao;

    @Override
    public Wtr getWtr(String key) {
        List<Wtr> wtrs = dao.selectByExample(getExample(key));
        return wtrs != null && wtrs.size() > 0 ? wtrs.get(0) : null;
    }


    @Override
    public boolean setWtr(Wtr wtr) {
        if (getWtr(wtr.getKey()) == null || wtr.getKey() == null) {
            return dao.insert(wtr) == 1;
        } else {
            wtr.setId(getWtr(wtr.getKey()).getId());
            return dao.updateByExample(wtr, getExample(wtr.getKey())) == 1;
        }
    }

    private WtrExample getExample(String key) {
        WtrExample example = new WtrExample();
        example.createCriteria().andKeyEqualTo(key);
        return example;
    }
}
