package cn.edu.wtu.wtr.media.util.education.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述：
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-03-15-21:07
 * @since 2021-03-15-21:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JWException extends RuntimeException {
    private String path;

    public JWException(String msg) {
        super(msg);
    }

    public JWException(String msg, String path) {
        super(msg);
        this.path = path;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.err.println("-------------------path:" + path);
    }
}
