package cn.edu.wtu.wtr.media.dao;

import cn.edu.wtu.wtr.media.object.Dynamic;
import cn.edu.wtu.wtr.media.object.DynamicExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Entity cn.edu.wtu.wtr.media.object.Dynamic
 */
@Mapper
@Repository
public interface DynamicDao {
    /**
     *
     * @mbg.generated 2021-02-27 21:30:07
     */
    long countByExample(DynamicExample example);

    /**
     *
     * @mbg.generated 2021-02-27 21:30:07
     */
    int deleteByExample(DynamicExample example);

    /**
     *
     * @mbg.generated 2021-02-27 21:30:07
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-02-27 21:30:07
     */
    int insert(Dynamic record);

    /**
     *
     * @mbg.generated 2021-02-27 21:30:07
     */
    int insertSelective(Dynamic record);

    /**
     *
     * @mbg.generated 2021-02-27 21:30:07
     */
    List<Dynamic> selectByExample(DynamicExample example);

    /**
     *
     * @mbg.generated 2021-02-27 21:30:07
     */
    Dynamic selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-02-27 21:30:07
     */
    int updateByExampleSelective(@Param("record") Dynamic record, @Param("example") DynamicExample example);

    /**
     *
     * @mbg.generated 2021-02-27 21:30:07
     */
    int updateByExample(@Param("record") Dynamic record, @Param("example") DynamicExample example);

    /**
     *
     * @mbg.generated 2021-02-27 21:30:07
     */
    int updateByPrimaryKeySelective(Dynamic record);

    /**
     *
     * @mbg.generated 2021-02-27 21:30:07
     */
    int updateByPrimaryKey(Dynamic record);

    int deleteByExample(int id);
}