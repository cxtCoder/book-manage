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
import pers.cxt.bms.database.entity.BmUserType;
import pers.cxt.bms.database.entity.BmUserTypeCriteria;

@Mapper
public interface BmUserTypeMapper {
    @SelectProvider(type=BmUserTypeSqlProvider.class, method="countByExample")
    long countByExample(BmUserTypeCriteria example);

    @DeleteProvider(type=BmUserTypeSqlProvider.class, method="deleteByExample")
    int deleteByExample(BmUserTypeCriteria example);

    @Delete({
        "delete from bm_user_type",
        "where type_id = #{typeId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String typeId);

    @Insert({
        "insert into bm_user_type (type_id, type_name)",
        "values (#{typeId,jdbcType=VARCHAR}, #{typeName,jdbcType=VARCHAR})"
    })
    int insert(BmUserType record);

    @InsertProvider(type=BmUserTypeSqlProvider.class, method="insertSelective")
    int insertSelective(BmUserType record);

    @SelectProvider(type=BmUserTypeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="type_id", property="typeId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="type_name", property="typeName", jdbcType=JdbcType.VARCHAR)
    })
    List<BmUserType> selectByExampleWithRowbounds(BmUserTypeCriteria example, RowBounds rowBounds);

    @SelectProvider(type=BmUserTypeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="type_id", property="typeId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="type_name", property="typeName", jdbcType=JdbcType.VARCHAR)
    })
    List<BmUserType> selectByExample(BmUserTypeCriteria example);

    @Select({
        "select",
        "type_id, type_name",
        "from bm_user_type",
        "where type_id = #{typeId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="type_id", property="typeId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="type_name", property="typeName", jdbcType=JdbcType.VARCHAR)
    })
    BmUserType selectByPrimaryKey(String typeId);

    @UpdateProvider(type=BmUserTypeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") BmUserType record, @Param("example") BmUserTypeCriteria example);

    @UpdateProvider(type=BmUserTypeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") BmUserType record, @Param("example") BmUserTypeCriteria example);

    @UpdateProvider(type=BmUserTypeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BmUserType record);

    @Update({
        "update bm_user_type",
        "set type_name = #{typeName,jdbcType=VARCHAR}",
        "where type_id = #{typeId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(BmUserType record);
}