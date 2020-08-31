package pers.cxt.bms.database.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import pers.cxt.bms.database.entity.BmContent;
import pers.cxt.bms.database.entity.BmContentCriteria.Criteria;
import pers.cxt.bms.database.entity.BmContentCriteria.Criterion;
import pers.cxt.bms.database.entity.BmContentCriteria;

public class BmContentSqlProvider {

    public String countByExample(BmContentCriteria example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("bm_content");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(BmContentCriteria example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("bm_content");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(BmContent record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("bm_content");
        
        if (record.getContentId() != null) {
            sql.VALUES("content_id", "#{contentId,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("`name`", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getUpContentId() != null) {
            sql.VALUES("up_content_id", "#{upContentId,jdbcType=VARCHAR}");
        }
        
        if (record.getLevel() != null) {
            sql.VALUES("`level`", "#{level,jdbcType=INTEGER}");
        }
        
        if (record.getAuthor() != null) {
            sql.VALUES("author", "#{author,jdbcType=VARCHAR}");
        }
        
        if (record.getStartPage() != null) {
            sql.VALUES("start_page", "#{startPage,jdbcType=INTEGER}");
        }
        
        if (record.getDescription() != null) {
            sql.VALUES("description", "#{description,jdbcType=VARCHAR}");
        }
        
        if (record.getBookId() != null) {
            sql.VALUES("book_id", "#{bookId,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExample(BmContentCriteria example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("content_id");
        } else {
            sql.SELECT("content_id");
        }
        sql.SELECT("`name`");
        sql.SELECT("up_content_id");
        sql.SELECT("`level`");
        sql.SELECT("author");
        sql.SELECT("start_page");
        sql.SELECT("description");
        sql.SELECT("book_id");
        sql.FROM("bm_content");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        BmContent record = (BmContent) parameter.get("record");
        BmContentCriteria example = (BmContentCriteria) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("bm_content");
        
        if (record.getContentId() != null) {
            sql.SET("content_id = #{record.contentId,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.SET("`name` = #{record.name,jdbcType=VARCHAR}");
        }
        
        if (record.getUpContentId() != null) {
            sql.SET("up_content_id = #{record.upContentId,jdbcType=VARCHAR}");
        }
        
        if (record.getLevel() != null) {
            sql.SET("`level` = #{record.level,jdbcType=INTEGER}");
        }
        
        if (record.getAuthor() != null) {
            sql.SET("author = #{record.author,jdbcType=VARCHAR}");
        }
        
        if (record.getStartPage() != null) {
            sql.SET("start_page = #{record.startPage,jdbcType=INTEGER}");
        }
        
        if (record.getDescription() != null) {
            sql.SET("description = #{record.description,jdbcType=VARCHAR}");
        }
        
        if (record.getBookId() != null) {
            sql.SET("book_id = #{record.bookId,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("bm_content");
        
        sql.SET("content_id = #{record.contentId,jdbcType=VARCHAR}");
        sql.SET("`name` = #{record.name,jdbcType=VARCHAR}");
        sql.SET("up_content_id = #{record.upContentId,jdbcType=VARCHAR}");
        sql.SET("`level` = #{record.level,jdbcType=INTEGER}");
        sql.SET("author = #{record.author,jdbcType=VARCHAR}");
        sql.SET("start_page = #{record.startPage,jdbcType=INTEGER}");
        sql.SET("description = #{record.description,jdbcType=VARCHAR}");
        sql.SET("book_id = #{record.bookId,jdbcType=VARCHAR}");
        
        BmContentCriteria example = (BmContentCriteria) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(BmContent record) {
        SQL sql = new SQL();
        sql.UPDATE("bm_content");
        
        if (record.getName() != null) {
            sql.SET("`name` = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getUpContentId() != null) {
            sql.SET("up_content_id = #{upContentId,jdbcType=VARCHAR}");
        }
        
        if (record.getLevel() != null) {
            sql.SET("`level` = #{level,jdbcType=INTEGER}");
        }
        
        if (record.getAuthor() != null) {
            sql.SET("author = #{author,jdbcType=VARCHAR}");
        }
        
        if (record.getStartPage() != null) {
            sql.SET("start_page = #{startPage,jdbcType=INTEGER}");
        }
        
        if (record.getDescription() != null) {
            sql.SET("description = #{description,jdbcType=VARCHAR}");
        }
        
        if (record.getBookId() != null) {
            sql.SET("book_id = #{bookId,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("content_id = #{contentId,jdbcType=VARCHAR}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, BmContentCriteria example, boolean includeExamplePhrase) {
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