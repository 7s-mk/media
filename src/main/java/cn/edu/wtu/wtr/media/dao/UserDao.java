package cn.edu.wtu.wtr.media.dao;

import cn.edu.wtu.wtr.media.object.User;
import cn.edu.wtu.wtr.media.object.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Entity cn.edu.wtu.wtr.media.object.User
 */
@Mapper
@Repository
public interface UserDao {
    /**
     * @mbg.generated
     */
    long countByExample(UserExample example);

    /**
     * @mbg.generated
     */
    int deleteByExample(UserExample example);

    /**
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int insert(User record);

    /**
     * @mbg.generated
     */
    int insertSelective(User record);

    /**
     * @mbg.generated
     */
    List<User> selectByExample(UserExample example);

    /**
     * @mbg.generated
     */
    User selectByPrimaryKey(Integer id);

    /**
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    /**
     * @mbg.generated
     */
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * @mbg.generated
     */
    int updateByPrimaryKey(User record);
}