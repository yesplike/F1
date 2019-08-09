package com.yesp.server.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.yesp.server.model.Cart;
import com.yesp.server.model.CartExample.Criteria;
import com.yesp.server.model.CartExample.Criterion;
import com.yesp.server.model.CartExample;
import java.util.List;
import java.util.Map;

public class CartSqlProvider {

    public String countByExample(CartExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("cart");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(CartExample example) {
        BEGIN();
        DELETE_FROM("cart");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(Cart record) {
        BEGIN();
        INSERT_INTO("cart");
        
        if (record.getUserid() != null) {
            VALUES("userid", "#{userid,jdbcType=INTEGER}");
        }
        
        if (record.getOrderid() != null) {
            VALUES("orderid", "#{orderid,jdbcType=INTEGER}");
        }
        
        if (record.getItemid() != null) {
            VALUES("itemid", "#{itemid,jdbcType=INTEGER}");
        }
        
        if (record.getQuantity() != null) {
            VALUES("quantity", "#{quantity,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String selectByExample(CartExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("userid");
        } else {
            SELECT("userid");
        }
        SELECT("orderid");
        SELECT("itemid");
        SELECT("quantity");
        FROM("cart");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Cart record = (Cart) parameter.get("record");
        CartExample example = (CartExample) parameter.get("example");
        
        BEGIN();
        UPDATE("cart");
        
        if (record.getUserid() != null) {
            SET("userid = #{record.userid,jdbcType=INTEGER}");
        }
        
        if (record.getOrderid() != null) {
            SET("orderid = #{record.orderid,jdbcType=INTEGER}");
        }
        
        if (record.getItemid() != null) {
            SET("itemid = #{record.itemid,jdbcType=INTEGER}");
        }
        
        if (record.getQuantity() != null) {
            SET("quantity = #{record.quantity,jdbcType=INTEGER}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("cart");
        
        SET("userid = #{record.userid,jdbcType=INTEGER}");
        SET("orderid = #{record.orderid,jdbcType=INTEGER}");
        SET("itemid = #{record.itemid,jdbcType=INTEGER}");
        SET("quantity = #{record.quantity,jdbcType=INTEGER}");
        
        CartExample example = (CartExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(Cart record) {
        BEGIN();
        UPDATE("cart");
        
        if (record.getQuantity() != null) {
            SET("quantity = #{quantity,jdbcType=INTEGER}");
        }
        
        WHERE("userid = #{userid,jdbcType=INTEGER}");
        WHERE("orderid = #{orderid,jdbcType=INTEGER}");
        WHERE("itemid = #{itemid,jdbcType=INTEGER}");
        
        return SQL();
    }

    protected void applyWhere(CartExample example, boolean includeExamplePhrase) {
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
            WHERE(sb.toString());
        }
    }
}