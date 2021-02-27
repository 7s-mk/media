package cn.edu.wtu.wtr.media.service.impl;

import cn.edu.wtu.wtr.media.dao.ContentsDao;
import cn.edu.wtu.wtr.media.object.Contents;
import cn.edu.wtu.wtr.media.object.ContentsExample;
import cn.edu.wtu.wtr.media.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentsService implements IContentService {
    @Autowired
    private ContentsDao dao;

    @Override
    public List<Contents> getAll(String key) {
        ContentsExample example = null;
        if (key != null) {
            example = new ContentsExample();
            example.createCriteria().andValueLike("%" + key + "%");
        }
        return dao.selectByExample(example);
    }

    @Override
    public boolean add(Contents contents) {
        if (contents.getCreateTime() == null) {
            contents.setCreateTime(new Date());
        }
        return dao.insertSelective(contents)==1;
    }

}
