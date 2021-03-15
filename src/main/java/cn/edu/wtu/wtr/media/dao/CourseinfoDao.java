package cn.edu.wtu.wtr.media.dao;

import cn.edu.wtu.wtr.media.object.Courseinfo;
import cn.edu.wtu.wtr.media.object.CourseinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Entity cn.edu.wtu.wtr.media.object.Courseinfo
 */
@Mapper
@Repository
public interface CourseinfoDao {
    /**
     *
     * @mbg.generated 2021-03-15 22:25:35
     */
    long countByExample(CourseinfoExample example);

    /**
     *
     * @mbg.generated 2021-03-15 22:25:35
     */
    int deleteByExample(CourseinfoExample example);

    /**
     *
     * @mbg.generated 2021-03-15 22:25:35
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-03-15 22:25:35
     */
    int insert(Courseinfo record);

    /**
     *
     * @mbg.generated 2021-03-15 22:25:35
     */
    int insertSelective(Courseinfo record);

    /**
     *
     * @mbg.generated 2021-03-15 22:25:35
     */
    List<Courseinfo> selectByExample(CourseinfoExample example);

    /**
     *
     * @mbg.generated 2021-03-15 22:25:35
     */
    Courseinfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2021-03-15 22:25:35
     */
    int updateByExampleSelective(@Param("record") Courseinfo record, @Param("example") CourseinfoExample example);

    /**
     *
     * @mbg.generated 2021-03-15 22:25:35
     */
    int updateByExample(@Param("record") Courseinfo record, @Param("example") CourseinfoExample example);

    /**
     *
     * @mbg.generated 2021-03-15 22:25:35
     */
    int updateByPrimaryKeySelective(Courseinfo record);

    /**
     *
     * @mbg.generated 2021-03-15 22:25:35
     */
    int updateByPrimaryKey(Courseinfo record);
}