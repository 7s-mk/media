package cn.edu.wtu.wtr.media.service;

/**
 * 描述：
 *
 * @author 郭沁雅
 * @version 1.0  2021-02-28-19:53
 * @since 2021-02-28-19:53
 */
public interface ICollectionService {
    /**
     * 添加哔哩哔哩单条记录
     *
     * @param mid mid
     * @return 消息 成功返回success
     */
    String addBili(String mid);

    /**
     * 更新哔哩哔哩的数据
     *
     * @return 消息
     */
    String updateBili();

    /**
     * 采集哔哩哔哩
     *
     * @return 消息
     */
    String collectionBili();
}
