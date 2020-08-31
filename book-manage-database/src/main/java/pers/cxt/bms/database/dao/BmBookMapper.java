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
import pers.cxt.bms.database.entity.BmBook;
import pers.cxt.bms.database.entity.BmBookCriteria;

@Mapper
public interface BmBookMapper {
    @SelectProvider(type=BmBookSqlProvider.class, method="countByExample")
    long countByExample(BmBookCriteria example);

    @DeleteProvider(type=BmBookSqlProvider.class, method="deleteByExample")
    int deleteByExample(BmBookCriteria example);

    @Delete({
        "delete from bm_book",
        "where book_id = #{bookId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String bookId);

    @Insert({
        "insert into bm_book (book_id, `name`, ",
        "sub_name, author, ",
        "publishing, published_date, ",
        "summary, remarks, ",
        "classify_id, classify, ",
        "series, series_order, ",
        "thumb_path, quantity, ",
        "province, city, ",
        "district, `position`, ",
        "user_id, create_time, ",
        "update_time)",
        "values (#{bookId,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{subName,jdbcType=VARCHAR}, #{author,jdbcType=VARCHAR}, ",
        "#{publishing,jdbcType=VARCHAR}, #{publishedDate,jdbcType=TIMESTAMP}, ",
        "#{summary,jdbcType=VARCHAR}, #{remarks,jdbcType=VARCHAR}, ",
        "#{classifyId,jdbcType=VARCHAR}, #{classify,jdbcType=VARCHAR}, ",
        "#{series,jdbcType=VARCHAR}, #{seriesOrder,jdbcType=INTEGER}, ",
        "#{thumbPath,jdbcType=VARCHAR}, #{quantity,jdbcType=INTEGER}, ",
        "#{province,jdbcType=VARCHAR}, #{city,jdbcType=VARCHAR}, ",
        "#{district,jdbcType=VARCHAR}, #{position,jdbcType=VARCHAR}, ",
        "#{userId,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(BmBook record);

    @InsertProvider(type=BmBookSqlProvider.class, method="insertSelective")
    int insertSelective(BmBook record);

    @SelectProvider(type=BmBookSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="book_id", property="bookId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_name", property="subName", jdbcType=JdbcType.VARCHAR),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="publishing", property="publishing", jdbcType=JdbcType.VARCHAR),
        @Result(column="published_date", property="publishedDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
        @Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR),
        @Result(column="classify_id", property="classifyId", jdbcType=JdbcType.VARCHAR),
        @Result(column="classify", property="classify", jdbcType=JdbcType.VARCHAR),
        @Result(column="series", property="series", jdbcType=JdbcType.VARCHAR),
        @Result(column="series_order", property="seriesOrder", jdbcType=JdbcType.INTEGER),
        @Result(column="thumb_path", property="thumbPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="quantity", property="quantity", jdbcType=JdbcType.INTEGER),
        @Result(column="province", property="province", jdbcType=JdbcType.VARCHAR),
        @Result(column="city", property="city", jdbcType=JdbcType.VARCHAR),
        @Result(column="district", property="district", jdbcType=JdbcType.VARCHAR),
        @Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BmBook> selectByExampleWithRowbounds(BmBookCriteria example, RowBounds rowBounds);

    @SelectProvider(type=BmBookSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="book_id", property="bookId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_name", property="subName", jdbcType=JdbcType.VARCHAR),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="publishing", property="publishing", jdbcType=JdbcType.VARCHAR),
        @Result(column="published_date", property="publishedDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
        @Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR),
        @Result(column="classify_id", property="classifyId", jdbcType=JdbcType.VARCHAR),
        @Result(column="classify", property="classify", jdbcType=JdbcType.VARCHAR),
        @Result(column="series", property="series", jdbcType=JdbcType.VARCHAR),
        @Result(column="series_order", property="seriesOrder", jdbcType=JdbcType.INTEGER),
        @Result(column="thumb_path", property="thumbPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="quantity", property="quantity", jdbcType=JdbcType.INTEGER),
        @Result(column="province", property="province", jdbcType=JdbcType.VARCHAR),
        @Result(column="city", property="city", jdbcType=JdbcType.VARCHAR),
        @Result(column="district", property="district", jdbcType=JdbcType.VARCHAR),
        @Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BmBook> selectByExample(BmBookCriteria example);

    @Select({
        "select",
        "book_id, `name`, sub_name, author, publishing, published_date, summary, remarks, ",
        "classify_id, classify, series, series_order, thumb_path, quantity, province, ",
        "city, district, `position`, user_id, create_time, update_time",
        "from bm_book",
        "where book_id = #{bookId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="book_id", property="bookId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_name", property="subName", jdbcType=JdbcType.VARCHAR),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="publishing", property="publishing", jdbcType=JdbcType.VARCHAR),
        @Result(column="published_date", property="publishedDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
        @Result(column="remarks", property="remarks", jdbcType=JdbcType.VARCHAR),
        @Result(column="classify_id", property="classifyId", jdbcType=JdbcType.VARCHAR),
        @Result(column="classify", property="classify", jdbcType=JdbcType.VARCHAR),
        @Result(column="series", property="series", jdbcType=JdbcType.VARCHAR),
        @Result(column="series_order", property="seriesOrder", jdbcType=JdbcType.INTEGER),
        @Result(column="thumb_path", property="thumbPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="quantity", property="quantity", jdbcType=JdbcType.INTEGER),
        @Result(column="province", property="province", jdbcType=JdbcType.VARCHAR),
        @Result(column="city", property="city", jdbcType=JdbcType.VARCHAR),
        @Result(column="district", property="district", jdbcType=JdbcType.VARCHAR),
        @Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    BmBook selectByPrimaryKey(String bookId);

    @UpdateProvider(type=BmBookSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") BmBook record, @Param("example") BmBookCriteria example);

    @UpdateProvider(type=BmBookSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") BmBook record, @Param("example") BmBookCriteria example);

    @UpdateProvider(type=BmBookSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BmBook record);

    @Update({
        "update bm_book",
        "set `name` = #{name,jdbcType=VARCHAR},",
          "sub_name = #{subName,jdbcType=VARCHAR},",
          "author = #{author,jdbcType=VARCHAR},",
          "publishing = #{publishing,jdbcType=VARCHAR},",
          "published_date = #{publishedDate,jdbcType=TIMESTAMP},",
          "summary = #{summary,jdbcType=VARCHAR},",
          "remarks = #{remarks,jdbcType=VARCHAR},",
          "classify_id = #{classifyId,jdbcType=VARCHAR},",
          "classify = #{classify,jdbcType=VARCHAR},",
          "series = #{series,jdbcType=VARCHAR},",
          "series_order = #{seriesOrder,jdbcType=INTEGER},",
          "thumb_path = #{thumbPath,jdbcType=VARCHAR},",
          "quantity = #{quantity,jdbcType=INTEGER},",
          "province = #{province,jdbcType=VARCHAR},",
          "city = #{city,jdbcType=VARCHAR},",
          "district = #{district,jdbcType=VARCHAR},",
          "`position` = #{position,jdbcType=VARCHAR},",
          "user_id = #{userId,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where book_id = #{bookId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(BmBook record);
}