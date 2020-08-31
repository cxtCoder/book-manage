package pers.cxt.bms.database.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import pers.cxt.bms.database.entity.BmBook;
import pers.cxt.bms.database.entity.BmBookCriteria.Criteria;
import pers.cxt.bms.database.entity.BmBookCriteria.Criterion;
import pers.cxt.bms.database.entity.BmBookCriteria;

public class BmBookSqlProvider {

    public String countByExample(BmBookCriteria example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("bm_book");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(BmBookCriteria example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("bm_book");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(BmBook record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("bm_book");
        
        if (record.getBookId() != null) {
            sql.VALUES("book_id", "#{bookId,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("`name`", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSubName() != null) {
            sql.VALUES("sub_name", "#{subName,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthor() != null) {
            sql.VALUES("author", "#{author,jdbcType=VARCHAR}");
        }
        
        if (record.getPublishing() != null) {
            sql.VALUES("publishing", "#{publishing,jdbcType=VARCHAR}");
        }
        
        if (record.getPublishedDate() != null) {
            sql.VALUES("published_date", "#{publishedDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSummary() != null) {
            sql.VALUES("summary", "#{summary,jdbcType=VARCHAR}");
        }
        
        if (record.getRemarks() != null) {
            sql.VALUES("remarks", "#{remarks,jdbcType=VARCHAR}");
        }
        
        if (record.getClassifyId() != null) {
            sql.VALUES("classify_id", "#{classifyId,jdbcType=VARCHAR}");
        }
        
        if (record.getClassify() != null) {
            sql.VALUES("classify", "#{classify,jdbcType=VARCHAR}");
        }
        
        if (record.getSeries() != null) {
            sql.VALUES("series", "#{series,jdbcType=VARCHAR}");
        }
        
        if (record.getSeriesOrder() != null) {
            sql.VALUES("series_order", "#{seriesOrder,jdbcType=INTEGER}");
        }
        
        if (record.getThumbPath() != null) {
            sql.VALUES("thumb_path", "#{thumbPath,jdbcType=VARCHAR}");
        }
        
        if (record.getQuantity() != null) {
            sql.VALUES("quantity", "#{quantity,jdbcType=INTEGER}");
        }
        
        if (record.getProvince() != null) {
            sql.VALUES("province", "#{province,jdbcType=VARCHAR}");
        }
        
        if (record.getCity() != null) {
            sql.VALUES("city", "#{city,jdbcType=VARCHAR}");
        }
        
        if (record.getDistrict() != null) {
            sql.VALUES("district", "#{district,jdbcType=VARCHAR}");
        }
        
        if (record.getPosition() != null) {
            sql.VALUES("`position`", "#{position,jdbcType=VARCHAR}");
        }
        
        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(BmBookCriteria example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("book_id");
        } else {
            sql.SELECT("book_id");
        }
        sql.SELECT("`name`");
        sql.SELECT("sub_name");
        sql.SELECT("author");
        sql.SELECT("publishing");
        sql.SELECT("published_date");
        sql.SELECT("summary");
        sql.SELECT("remarks");
        sql.SELECT("classify_id");
        sql.SELECT("classify");
        sql.SELECT("series");
        sql.SELECT("series_order");
        sql.SELECT("thumb_path");
        sql.SELECT("quantity");
        sql.SELECT("province");
        sql.SELECT("city");
        sql.SELECT("district");
        sql.SELECT("`position`");
        sql.SELECT("user_id");
        sql.SELECT("create_time");
        sql.SELECT("update_time");
        sql.FROM("bm_book");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        BmBook record = (BmBook) parameter.get("record");
        BmBookCriteria example = (BmBookCriteria) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("bm_book");
        
        if (record.getBookId() != null) {
            sql.SET("book_id = #{record.bookId,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("`name` = #{record.name,jdbcType=VARCHAR}");
        }
        
        if (record.getSubName() != null) {
            sql.SET("sub_name = #{record.subName,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthor() != null) {
            sql.SET("author = #{record.author,jdbcType=VARCHAR}");
        }
        
        if (record.getPublishing() != null) {
            sql.SET("publishing = #{record.publishing,jdbcType=VARCHAR}");
        }
        
        if (record.getPublishedDate() != null) {
            sql.SET("published_date = #{record.publishedDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSummary() != null) {
            sql.SET("summary = #{record.summary,jdbcType=VARCHAR}");
        }
        
        if (record.getRemarks() != null) {
            sql.SET("remarks = #{record.remarks,jdbcType=VARCHAR}");
        }
        
        if (record.getClassifyId() != null) {
            sql.SET("classify_id = #{record.classifyId,jdbcType=VARCHAR}");
        }
        
        if (record.getClassify() != null) {
            sql.SET("classify = #{record.classify,jdbcType=VARCHAR}");
        }
        
        if (record.getSeries() != null) {
            sql.SET("series = #{record.series,jdbcType=VARCHAR}");
        }
        
        if (record.getSeriesOrder() != null) {
            sql.SET("series_order = #{record.seriesOrder,jdbcType=INTEGER}");
        }
        
        if (record.getThumbPath() != null) {
            sql.SET("thumb_path = #{record.thumbPath,jdbcType=VARCHAR}");
        }
        
        if (record.getQuantity() != null) {
            sql.SET("quantity = #{record.quantity,jdbcType=INTEGER}");
        }
        
        if (record.getProvince() != null) {
            sql.SET("province = #{record.province,jdbcType=VARCHAR}");
        }
        
        if (record.getCity() != null) {
            sql.SET("city = #{record.city,jdbcType=VARCHAR}");
        }
        
        if (record.getDistrict() != null) {
            sql.SET("district = #{record.district,jdbcType=VARCHAR}");
        }
        
        if (record.getPosition() != null) {
            sql.SET("`position` = #{record.position,jdbcType=VARCHAR}");
        }
        
        if (record.getUserId() != null) {
            sql.SET("user_id = #{record.userId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("bm_book");
        
        sql.SET("book_id = #{record.bookId,jdbcType=VARCHAR}");
        sql.SET("`name` = #{record.name,jdbcType=VARCHAR}");
        sql.SET("sub_name = #{record.subName,jdbcType=VARCHAR}");
        sql.SET("author = #{record.author,jdbcType=VARCHAR}");
        sql.SET("publishing = #{record.publishing,jdbcType=VARCHAR}");
        sql.SET("published_date = #{record.publishedDate,jdbcType=TIMESTAMP}");
        sql.SET("summary = #{record.summary,jdbcType=VARCHAR}");
        sql.SET("remarks = #{record.remarks,jdbcType=VARCHAR}");
        sql.SET("classify_id = #{record.classifyId,jdbcType=VARCHAR}");
        sql.SET("classify = #{record.classify,jdbcType=VARCHAR}");
        sql.SET("series = #{record.series,jdbcType=VARCHAR}");
        sql.SET("series_order = #{record.seriesOrder,jdbcType=INTEGER}");
        sql.SET("thumb_path = #{record.thumbPath,jdbcType=VARCHAR}");
        sql.SET("quantity = #{record.quantity,jdbcType=INTEGER}");
        sql.SET("province = #{record.province,jdbcType=VARCHAR}");
        sql.SET("city = #{record.city,jdbcType=VARCHAR}");
        sql.SET("district = #{record.district,jdbcType=VARCHAR}");
        sql.SET("`position` = #{record.position,jdbcType=VARCHAR}");
        sql.SET("user_id = #{record.userId,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        
        BmBookCriteria example = (BmBookCriteria) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(BmBook record) {
        SQL sql = new SQL();
        sql.UPDATE("bm_book");
        
        if (record.getName() != null) {
            sql.SET("`name` = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getSubName() != null) {
            sql.SET("sub_name = #{subName,jdbcType=VARCHAR}");
        }
        
        if (record.getAuthor() != null) {
            sql.SET("author = #{author,jdbcType=VARCHAR}");
        }
        
        if (record.getPublishing() != null) {
            sql.SET("publishing = #{publishing,jdbcType=VARCHAR}");
        }
        
        if (record.getPublishedDate() != null) {
            sql.SET("published_date = #{publishedDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSummary() != null) {
            sql.SET("summary = #{summary,jdbcType=VARCHAR}");
        }
        
        if (record.getRemarks() != null) {
            sql.SET("remarks = #{remarks,jdbcType=VARCHAR}");
        }
        
        if (record.getClassifyId() != null) {
            sql.SET("classify_id = #{classifyId,jdbcType=VARCHAR}");
        }
        
        if (record.getClassify() != null) {
            sql.SET("classify = #{classify,jdbcType=VARCHAR}");
        }
        
        if (record.getSeries() != null) {
            sql.SET("series = #{series,jdbcType=VARCHAR}");
        }
        
        if (record.getSeriesOrder() != null) {
            sql.SET("series_order = #{seriesOrder,jdbcType=INTEGER}");
        }
        
        if (record.getThumbPath() != null) {
            sql.SET("thumb_path = #{thumbPath,jdbcType=VARCHAR}");
        }
        
        if (record.getQuantity() != null) {
            sql.SET("quantity = #{quantity,jdbcType=INTEGER}");
        }
        
        if (record.getProvince() != null) {
            sql.SET("province = #{province,jdbcType=VARCHAR}");
        }
        
        if (record.getCity() != null) {
            sql.SET("city = #{city,jdbcType=VARCHAR}");
        }
        
        if (record.getDistrict() != null) {
            sql.SET("district = #{district,jdbcType=VARCHAR}");
        }
        
        if (record.getPosition() != null) {
            sql.SET("`position` = #{position,jdbcType=VARCHAR}");
        }
        
        if (record.getUserId() != null) {
            sql.SET("user_id = #{userId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("book_id = #{bookId,jdbcType=VARCHAR}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, BmBookCriteria example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}