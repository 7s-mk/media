package cn.edu.wtu.wtr.media.controller;

import cn.edu.wtu.wtr.media.object.Office;
import cn.edu.wtu.wtr.media.service.ICollectionService;
import cn.edu.wtu.wtr.media.util.HttpContext;
import cn.edu.wtu.wtr.media.util.PopUps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述：数据采集
 *
 * @author 郭沁雅
 * @version 1.0  2021-02-28-18:09
 * @since 2021-02-28-18:09
 */
@RequestMapping("/collection")
@Controller
public class CollectionControl {

    @Autowired
    private ICollectionService service;

    @GetMapping
    public String collection() {
        return "collection";
    }

    @GetMapping("/bilibili/single")
    public String singleBili(Model model, String mid) {
        if (!HttpContext.checkOffice(Office.干事))
            return PopUps.unOffice(model, Office.干事);

        String s = service.addBili(mid);
        if (s.equals("success"))
            return PopUps.info(model, "采集成功！");
        return PopUps.info(model, "采集失败！" + s);
    }

    @GetMapping("/bilibili/update")
    public String updateBili(Model model) {
        if (!HttpContext.checkOffice(Office.干事))
            return PopUps.unOffice(model, Office.干事);
        return PopUps.info(model, service.updateBili());
    }

    @GetMapping("/bilibili/all")
    public String queryAllBili(Model model) {
        if (!HttpContext.checkOffice(Office.干事))
            return PopUps.unOffice(model, Office.干事);
        return PopUps.info(model, service.collectionBili());
    }
}
