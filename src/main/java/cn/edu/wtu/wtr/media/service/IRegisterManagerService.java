package cn.edu.wtu.wtr.media.service;

import cn.edu.wtu.wtr.media.object.wtrsystem.WTRRegisterManage;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-16-16:53
 * @since 2021-03-16-16:53
 */
public interface IRegisterManagerService {
    boolean setRC(WTRRegisterManage.InvitationCode code);

    boolean setISR(boolean isR);

    boolean remRC(String code);

    WTRRegisterManage getRC();
}
