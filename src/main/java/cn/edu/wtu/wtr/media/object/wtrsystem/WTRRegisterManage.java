package cn.edu.wtu.wtr.media.object.wtrsystem;

import cn.edu.wtu.wtr.media.object.Office;
import cn.edu.wtu.wtr.media.object.Wtr;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：注册管理
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-16-15:39
 * @since 2021-03-16-15:39
 */
@Data
public class WTRRegisterManage {
    public static final String KEY = "wt000000";

    /**
     * 是否开放
     */
    private boolean open = false;

    private List<InvitationCode> invitationCode = new ArrayList<>();

    public static WTRRegisterManage build(Wtr wtr) {
        if (wtr == null || !KEY.equals(wtr.getKey()))
            return null;
        WTRRegisterManage registerManage = new WTRRegisterManage();
        try {
            JSONObject json = JSONObject.parseObject(wtr.getValue());
            registerManage.setOpen(json.getObject("open", Boolean.class));
            registerManage.setInvitationCode(JSONArray.parseArray(json.getString("invitationCode"), InvitationCode.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registerManage;
    }

    public Wtr toWtr() {
        Wtr wtr = new Wtr();
        wtr.setKey(KEY);
        wtr.setValue(JSONObject.toJSONString(this));
        return wtr;
    }

    /**
     * 添加
     *
     * @param code 邀请码
     * @return ok?
     */
    public boolean add(InvitationCode code) {
        if (invitationCode == null)
            invitationCode = new ArrayList<>();
        for (InvitationCode invitation : invitationCode) {
            if (invitation.getCode().equals(code.getCode()))
                throw new RuntimeException("该邀请码已经存在！请更换！");
        }
        return invitationCode.add(code);
    }

    /**
     * 移除
     *
     * @param code 邀请码
     * @return ok？
     */
    public boolean remove(String code) {
        if (invitationCode == null)
            return true;
        for (InvitationCode invitation : invitationCode) {
            if (invitation.getCode().equals(code))
                return invitationCode.remove(invitation);
        }
        throw new RuntimeException("该邀请码不存在");
    }

    /**
     * 获取邀请码
     *
     * @param code 邀请码
     * @return 对象
     */
    public InvitationCode get(String code) {
        if (invitationCode != null)
            for (InvitationCode invitation : invitationCode) {
                if (invitation.getCode().equals(code))
                    return invitation;
            }
        throw new RuntimeException("该邀请码不存在");
    }


    @Data
    public static class InvitationCode {
        /**
         * 邀请码
         */
        private String code;
        /**
         * 部门
         */
        private String department;
        /**
         * 添加后的级别
         */
        private Office office;

        /**
         * 添加者
         */
        private String name;

        /**
         * 添加时间
         */
        private LocalDateTime dateTime;
    }
}
