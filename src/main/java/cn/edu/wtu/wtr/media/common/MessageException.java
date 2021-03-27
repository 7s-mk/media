package cn.edu.wtu.wtr.media.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * ----------------
 *
 * @author lpc
 * @version 1.0 2021/3/27
 * ----------------
 * @since 2021/3/27-下午10:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageException extends RuntimeException {
    private int code = -1;
    private String info;
    private LocalDateTime dateTime;
    private String desc;

    /**
     * 构造函数
     *
     * @param info 信息
     */
    public MessageException(String info) {
        super(info);
        this.info = info;
        this.dateTime = LocalDateTime.now();
        this.desc = info;
    }
}
