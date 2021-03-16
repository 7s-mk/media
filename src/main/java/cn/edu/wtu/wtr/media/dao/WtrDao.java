package cn.edu.wtu.wtr.media.dao;

import cn.edu.wtu.wtr.media.object.Wtr;
import cn.edu.wtu.wtr.media.object.WtrExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Entity cn.edu.wtu.wtr.media.object.Wtr
 */
@Mapper
@Repository
public interface WtrDao {
    /**
     *
     * @mbg.generated 2021-03-16 15:46:41
     */
    long countByExample(WtrExample example);

    /**
     *
     * @mbg.generated 2021-03-16 15:46:41
     */
    int deleteByExample(WtrExample example);

    /**
     *
     * @mbg.generated 2021-03-16 15:46:41
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-03-16 15:46:41
     */
    int insert(Wtr record);

    /**
     *
     * @mbg.generated 2021-03-16 15:46:41
     */
    int insertSelective(Wtr record);

    /**
     *
     * @mbg.generated 2021-03-16 15:46:41
     */
    List<Wtr> selectByExample(WtrExample example);

    /**
     *
     * @mbg.generated 2021-03-16 15:46:41
     */
    Wtr selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-03-16 15:46:41
     */
    int updateByExampleSelective(@Param("record") Wtr record, @Param("example") WtrExample example);

    /**
     *
     * @mbg.generated 2021-03-16 15:46:41
     */
    int updateByExample(@Param("record") Wtr record, @Param("example") WtrExample example);

    /**
     *
     * @mbg.generated 2021-03-16 15:46:41
     */
    int updateByPrimaryKeySelective(Wtr record);

    /**
     *
     * @mbg.generated 2021-03-16 15:46:41
     */
    int updateByPrimaryKey(Wtr record);
}