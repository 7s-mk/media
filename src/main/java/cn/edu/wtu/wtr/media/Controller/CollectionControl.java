package cn.edu.wtu.wtr.media.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述：数据采集
 *
 * @author lpc lpc@hll520.cn
 * @version 1.0  2021-02-28-18:09
 * @since 2021-02-28-18:09
 */
@RequestMapping("/collection")
@Controller
public class CollectionControl {

    @GetMapping
    public String collection() {
        return "collection";
    }
}
