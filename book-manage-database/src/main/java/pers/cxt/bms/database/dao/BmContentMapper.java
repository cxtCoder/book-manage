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
import pers.cxt.bms.database.entity.BmContent;
import pers.cxt.bms.database.entity.BmContentCriteria;

@Mapper
public interface BmContentMapper {
    @SelectProvider(type=BmContentSqlProvider.class, method="countByExample")
    long countByExample(BmContentCriteria example);

    @DeleteProvider(type=BmContentSqlProvider.class, method="deleteByExample")
    int deleteByExample(BmContentCriteria example);

    @Delete({
        "delete from bm_content",
        "where content_id = #{contentId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String contentId);

    @Insert({
        "insert into bm_content (content_id, `name`, ",
        "up_content_id, `level`, ",
        "author, start_page, ",
        "description, book_id)",
        "values (#{contentId,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{upContentId,jdbcType=VARCHAR}, #{level,jdbcType=INTEGER}, ",
        "#{author,jdbcType=VARCHAR}, #{startPage,jdbcType=INTEGER}, ",
        "#{description,jdbcType=VARCHAR}, #{bookId,jdbcType=VARCHAR})"
    })
    int insert(BmContent record);

    @InsertProvider(type=BmContentSqlProvider.class, method="insertSelective")
    int insertSelective(BmContent record);

    @SelectProvider(type=BmContentSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="content_id", property="contentId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="up_content_id", property="upContentId", jdbcType=JdbcType.VARCHAR),
        @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_page", property="startPage", jdbcType=JdbcType.INTEGER),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="book_id", property="bookId", jdbcType=JdbcType.VARCHAR)
    })
    List<BmContent> selectByExampleWithRowbounds(BmContentCriteria example, RowBounds rowBounds);

    @SelectProvider(type=BmContentSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="content_id", property="contentId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="up_content_id", property="upContentId", jdbcType=JdbcType.VARCHAR),
        @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_page", property="startPage", jdbcType=JdbcType.INTEGER),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="book_id", property="bookId", jdbcType=JdbcType.VARCHAR)
    })
    List<BmContent> selectByExample(BmContentCriteria example);

    @Select({
        "select",
        "content_id, `name`, up_content_id, `level`, author, start_page, description, ",
        "book_id",
        "from bm_content",
        "where content_id = #{contentId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="content_id", property="contentId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="up_content_id", property="upContentId", jdbcType=JdbcType.VARCHAR),
        @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_page", property="startPage", jdbcType=JdbcType.INTEGER),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="book_id", property="bookId", jdbcType=JdbcType.VARCHAR)
    })
    BmContent selectByPrimaryKey(String contentId);

    @UpdateProvider(type=BmContentSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") BmContent record, @Param("example") BmContentCriteria example);

    @UpdateProvider(type=BmContentSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") BmContent record, @Param("example") BmContentCriteria example);

    @UpdateProvider(type=BmContentSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BmContent record);

    @Update({
        "update bm_content",
        "set `name` = #{name,jdbcType=VARCHAR},",
          "up_content_id = #{upContentId,jdbcType=VARCHAR},",
          "`level` = #{level,jdbcType=INTEGER},",
          "author = #{author,jdbcType=VARCHAR},",
          "start_page = #{startPage,jdbcType=INTEGER},",
          "description = #{description,jdbcType=VARCHAR},",
          "book_id = #{bookId,jdbcType=VARCHAR}",
        "where content_id = #{contentId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(BmContent record);
}