package cn.edu.wtu.wtr.media.object.wtrsystem;

import cn.edu.wtu.wtr.media.object.Office;
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
public class registerManage {
    /**
     * 是否开放
     */
    private boolean open = false;

    private List<InvitationCode> invitationCode = new ArrayList<>();

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
