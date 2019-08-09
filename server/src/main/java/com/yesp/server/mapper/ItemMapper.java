package com.yesp.server.mapper;

import com.yesp.server.model.Item;
import com.yesp.server.model.ItemExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface ItemMapper {
    @SelectProvider(type=ItemSqlProvider.class, method="countByExample")
    int countByExample(ItemExample example);

    @DeleteProvider(type=ItemSqlProvider.class, method="deleteByExample")
    int deleteByExample(ItemExample example);

    @Delete({
        "delete from item",
        "where itemid = #{itemid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer itemid);

    @Insert({
        "insert into item (itemid, itemno, ",
        "productid, listprice, ",
        "attrl)",
        "values (#{itemid,jdbcType=INTEGER}, #{itemno,jdbcType=VARCHAR}, ",
        "#{productid,jdbcType=INTEGER}, #{listprice,jdbcType=REAL}, ",
        "#{attrl,jdbcType=CHAR})"
    })
    int insert(Item record);

    @InsertProvider(type=ItemSqlProvider.class, method="insertSelective")
    int insertSelective(Item record);

    @SelectProvider(type=ItemSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="itemid", property="itemid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="itemno", property="itemno", jdbcType=JdbcType.VARCHAR),
        @Result(column="productid", property="productid", jdbcType=JdbcType.INTEGER),
        @Result(column="listprice", property="listprice", jdbcType=JdbcType.REAL),
        @Result(column="attrl", property="attrl", jdbcType=JdbcType.CHAR),
            @Result( property = "product" ,column = "productid" ,
            one = @One(select = "com.yesp.server.mapper.ProductMapper.selectByPrimaryKey"))
    })
    List<Item> selectByExample(ItemExample example);

    @Select({
        "select",
        "itemid, itemno, productid, listprice, attrl",
        "from item",
        "where itemid = #{itemid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="itemid", property="itemid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="itemno", property="itemno", jdbcType=JdbcType.VARCHAR),
        @Result(column="productid", property="productid", jdbcType=JdbcType.INTEGER),
        @Result(column="listprice", property="listprice", jdbcType=JdbcType.REAL),
        @Result(column="attrl", property="attrl", jdbcType=JdbcType.CHAR)
    })
    Item selectByPrimaryKey(Integer itemid);

    @UpdateProvider(type=ItemSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Item record, @Param("example") ItemExample example);

    @UpdateProvider(type=ItemSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Item record, @Param("example") ItemExample example);

    @UpdateProvider(type=ItemSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Item record);

    @Update({
        "update item",
        "set itemno = #{itemno,jdbcType=VARCHAR},",
          "productid = #{productid,jdbcType=INTEGER},",
          "listprice = #{listprice,jdbcType=REAL},",
          "attrl = #{attrl,jdbcType=CHAR}",
        "where itemid = #{itemid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Item record);
}