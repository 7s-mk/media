package cn.edu.wtu.wtr.media.service;

import cn.edu.wtu.wtr.media.object.Wtr;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-16-16:05
 * @since 2021-03-16-16:05
 */
public interface IWTRService {
    Wtr getWtr(String key);

    /**
     * 设置 存在为覆盖
     * @param wtr key
     * @return va
     */
    boolean setWtr(Wtr wtr);
}
