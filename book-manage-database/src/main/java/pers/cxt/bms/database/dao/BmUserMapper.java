package pers.cxt.bms.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import pers.cxt.bms.database.entity.BmUser;
import pers.cxt.bms.database.entity.BmUserCriteria;

@Mapper
public interface BmUserMapper {
    @SelectProvider(type=BmUserSqlProvider.class, method="countByExample")
    long countByExample(BmUserCriteria example);

    @DeleteProvider(type=BmUserSqlProvider.class, method="deleteByExample")
    int deleteByExample(BmUserCriteria example);

    @Delete({
        "delete from bm_user",
        "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String userId);

    @Insert({
        "insert into bm_user (user_id, user_pwd, ",
        "user_name, type_id, ",
        "create_time, update_time)",
        "values (#{userId,jdbcType=VARCHAR}, #{userPwd,jdbcType=VARCHAR}, ",
        "#{userName,jdbcType=VARCHAR}, #{typeId,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(BmUser record);

    @InsertProvider(type=BmUserSqlProvider.class, method="insertSelective")
    int insertSelective(BmUser record);

    @SelectProvider(type=BmUserSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="user_pwd", property="userPwd", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="type_id", property="typeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BmUser> selectByExampleWithRowbounds(BmUserCriteria example, RowBounds rowBounds);

    @SelectProvider(type=BmUserSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="user_pwd", property="userPwd", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="type_id", property="typeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BmUser> selectByExample(BmUserCriteria example);

    @Select({
        "select",
        "user_id, user_pwd, user_name, type_id, create_time, update_time",
        "from bm_user",
        "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="user_pwd", property="userPwd", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="type_id", property="typeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    BmUser selectByPrimaryKey(String userId);

    @UpdateProvider(type=BmUserSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") BmUser record, @Param("example") BmUserCriteria example);

    @UpdateProvider(type=BmUserSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") BmUser record, @Param("example") BmUserCriteria example);

    @UpdateProvider(type=BmUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BmUser record);

    @Update({
        "update bm_user",
        "set user_pwd = #{userPwd,jdbcType=VARCHAR},",
          "user_name = #{userName,jdbcType=VARCHAR},",
          "type_id = #{typeId,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where user_id = #{userId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(BmUser record);
}