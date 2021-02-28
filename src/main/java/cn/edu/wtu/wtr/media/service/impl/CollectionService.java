package cn.edu.wtu.wtr.media.service.impl;

import cn.edu.wtu.wtr.media.dao.DynamicDao;
import cn.edu.wtu.wtr.media.object.DynamicExample;
import cn.edu.wtu.wtr.media.service.ICollectionService;
import cn.hll520.linling.biliClient.BiliClient;
import cn.hll520.linling.biliClient.BiliClientFactor;
import cn.hll520.linling.biliClient.model.dynamic.Dynamic;
import cn.hll520.linling.biliClient.utils.TransViewUri;
import cn.hll520.linling.biliClient.utils.dynamic.DynamicTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

/**
 * 描述：
 *
 * @author 郭沁雅
 * @version 1.0  2021-02-28-19:55
 * @since 2021-02-28-19:55
 */
@Service
public class CollectionService implements ICollectionService {

    // 哔哩哔哩
    private BiliClient biliClient = BiliClientFactor.getClient();
    @Autowired
    private DynamicDao dao;

    /**
     * 添加哔哩哔哩单条记录
     *
     * @param mid mid
     * @return 消息 成功返回success
     */
    @Override
    public String addBili(String mid) {
        if (mid == null || mid.isEmpty())
            return "采集动态ID不能为空！";
        Long id;
        try {
            id = new Long(mid);
        } catch (Exception e) {
            return "输入的哔哩哔哩动态必须为数字！当前为:" + mid;
        }
        Dynamic bilibili = biliClient.dynamic().withDynamicId(id).get();
        cn.edu.wtu.wtr.media.object.Dynamic dynamic = biliToDynamic(bilibili);
        try {
            if (dao.insertSelective(dynamic) == 1)
                return "success";
            return "fail";
        } catch (Exception e) {
            return "当前动态已经收录过了！";
        }
    }

    /**
     * 更新哔哩哔哩的数据
     *
     * @return 消息
     */
    @Override
    public String updateBili() {
        DynamicExample example = new DynamicExample();
        example.createCriteria().andPlatformEqualTo("哔哩哔哩");
        Date date = new Date();
        dao.selectByExample(example).stream()
                .filter(dynamic -> dynamic.getDid() != null && !dynamic.getDid().isEmpty())
                .forEach(dynamic -> {
                    date.count++;
                    try {
                        Dynamic bilibili = biliClient.dynamic()
                                .withDynamicId(Long.valueOf(dynamic.getDid())).get();
                        updateDate(bilibili, dynamic);
                        DynamicExample dynamicExample = new DynamicExample();
                        dynamicExample.createCriteria().andPlatformEqualTo("哔哩哔哩")
                                .andDidEqualTo(dynamic.getDid());
                        if (dao.updateByExampleSelective(dynamic, dynamicExample) == 1)
                            date.success++;
                        else
                            date.msg.append("> 更新异常：").append(dynamic.getDid()).append("---更新数据失败\\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("更新错误");
                        date.msg.append("> 更新异常：").append(dynamic.getDid()).append("---获取数据错误\\n");
                    }
                });
        return "共" + date.count + "条数据,成功更新" + date.success + "条\\n" + date.msg;
    }

    /**
     * 采集哔哩哔哩
     *
     * @return 消息
     */
    @Override
    public String collectionBili() {
        long time = System.currentTimeMillis();
        Date date = new Date();

        DynamicTools.queryAll(biliClient, 392819792L).forEach(bilibili -> {
            cn.edu.wtu.wtr.media.object.Dynamic dynamic = biliToDynamic(bilibili);
            try {
                if (dao.insertSelective(dynamic) == 1)
                    date.success++;
            } catch (Exception e) {
                date.count++;
            }
        });
        return "共采集到数据:" + (date.count + date.success)
                + "条\\n其中新更新：" + date.success
                + "条\\n之前已经收录" + date.count
                + "条\\n共用时：" + (System.currentTimeMillis() - time) / 1000.0 + "秒";
    }

    /**
     * 数据类
     */
    private static class Date {
        int count = 0;
        int success = 0;
        StringBuilder msg = new StringBuilder();
    }

    /**
     * 哔哩哔哩动态转换为Dynamic
     *
     * @param bilibili 哔哩哔哩
     * @return dynamic
     */
    private cn.edu.wtu.wtr.media.object.Dynamic biliToDynamic(Dynamic bilibili) {
        if (bilibili != null) {
            try {
                cn.edu.wtu.wtr.media.object.Dynamic dynamic = new cn.edu.wtu.wtr.media.object.Dynamic();
                dynamic.setDid(String.valueOf(bilibili.getData().getDynamic_id()));
                if (bilibili.getUid() != null)
                    dynamic.setAccount(bilibili.getUid().toString());
                // 时间
                dynamic.setCreateTime(bilibili.getData().createTime()
                        .toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDateTime());
                // 平台
                dynamic.setPlatform("哔哩哔哩");
                // url
                dynamic.setUrl(TransViewUri.trans(bilibili));
                // 类型
                dynamic.setType(bilibili.getType().toString());
                // 内容
                if (bilibili.getDetail() == null) {
                    if (bilibili.getRepost() != null) {
                        dynamic.setValue(bilibili.getRepost().getContent());
                    } else if (bilibili.getVideo() != null) {
                        dynamic.setValue(bilibili.getVideo().getDynamic());
                        dynamic.setStyle(bilibili.getVideo().getTitle());
                    }
                } else {
                    dynamic.setValue(bilibili.getDetail().getDescription());
                    dynamic.setStyle(bilibili.getDetail().getCategory());
                }
                // 数据
                updateDate(bilibili, dynamic);
                return dynamic;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("转换遇到错误！");
            }
        }
        return null;
    }

    /**
     * 更新数据
     *
     * @param bilibili 哔哩哔哩
     * @param dynamic  动态
     */
    private void updateDate(Dynamic bilibili, cn.edu.wtu.wtr.media.object.Dynamic dynamic) {
        // 数据
        dynamic.setView(Math.toIntExact(bilibili.getData().getView()));
        dynamic.setTransmit(Math.toIntExact(bilibili.getData().getRepost()));
        dynamic.setComment(Math.toIntExact(bilibili.getData().getComment()));
        dynamic.setLike(Math.toIntExact(bilibili.getData().getLike()));
        if (bilibili.getType() == Dynamic.DType.VIDEO)
            dynamic.setCoin(Math.toIntExact(bilibili.getVideo().getStat().getCoin()));
    }
}
