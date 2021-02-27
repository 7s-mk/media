package cn.edu.wtu.wtr.media.dao;

import cn.edu.wtu.wtr.media.object.Contents;
import cn.edu.wtu.wtr.media.object.ContentsExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Entity cn.edu.wtu.wtr.media.object.Contents
 */
@Mapper
@Repository
public interface ContentsDao {
    /**
     * @mbg.generated
     */
    long countByExample(ContentsExample example);

    /**
     * @mbg.generated
     */
    int deleteByExample(ContentsExample example);

    /**
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int insert(Contents record);

    /**
     * @mbg.generated
     */
    int insertSelective(Contents record);

    /**
     * @mbg.generated
     */
    List<Contents> selectByExample(ContentsExample example);

    /**
     * @mbg.generated
     */
    Contents selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Contents record, @Param("example") ContentsExample example);

    /**
     * @mbg.generated
     */
    int updateByExample(@Param("record") Contents record, @Param("example") ContentsExample example);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Contents record);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(Contents record);
}