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
import pers.cxt.bms.database.entity.BmClassify;
import pers.cxt.bms.database.entity.BmClassifyCriteria;

@Mapper
public interface BmClassifyMapper {
    @SelectProvider(type=BmClassifySqlProvider.class, method="countByExample")
    long countByExample(BmClassifyCriteria example);

    @DeleteProvider(type=BmClassifySqlProvider.class, method="deleteByExample")
    int deleteByExample(BmClassifyCriteria example);

    @Delete({
        "delete from bm_classify",
        "where classify_id = #{classifyId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String classifyId);

    @Insert({
        "insert into bm_classify (classify_id, classify, ",
        "sub_classify, create_time, ",
        "update_time)",
        "values (#{classifyId,jdbcType=VARCHAR}, #{classify,jdbcType=VARCHAR}, ",
        "#{subClassify,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(BmClassify record);

    @InsertProvider(type=BmClassifySqlProvider.class, method="insertSelective")
    int insertSelective(BmClassify record);

    @SelectProvider(type=BmClassifySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="classify_id", property="classifyId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="classify", property="classify", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_classify", property="subClassify", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BmClassify> selectByExampleWithRowbounds(BmClassifyCriteria example, RowBounds rowBounds);

    @SelectProvider(type=BmClassifySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="classify_id", property="classifyId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="classify", property="classify", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_classify", property="subClassify", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BmClassify> selectByExample(BmClassifyCriteria example);

    @Select({
        "select",
        "classify_id, classify, sub_classify, create_time, update_time",
        "from bm_classify",
        "where classify_id = #{classifyId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="classify_id", property="classifyId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="classify", property="classify", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_classify", property="subClassify", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    BmClassify selectByPrimaryKey(String classifyId);

    @UpdateProvider(type=BmClassifySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") BmClassify record, @Param("example") BmClassifyCriteria example);

    @UpdateProvider(type=BmClassifySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") BmClassify record, @Param("example") BmClassifyCriteria example);

    @UpdateProvider(type=BmClassifySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BmClassify record);

    @Update({
        "update bm_classify",
        "set classify = #{classify,jdbcType=VARCHAR},",
          "sub_classify = #{subClassify,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where classify_id = #{classifyId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(BmClassify record);
}