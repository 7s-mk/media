package cn.edu.wtu.wtr.media.service;

import cn.edu.wtu.wtr.media.object.Contents;

import java.util.List;

public interface IContentService {
    List<Contents> getAll(String key);
    boolean add(Contents contents);
}
