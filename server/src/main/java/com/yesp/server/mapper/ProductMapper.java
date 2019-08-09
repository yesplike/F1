package com.yesp.server.mapper;

import com.yesp.server.model.Product;
import com.yesp.server.model.ProductExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface ProductMapper {
    @SelectProvider(type=ProductSqlProvider.class, method="countByExample")
    int countByExample(ProductExample example);

    @DeleteProvider(type=ProductSqlProvider.class, method="deleteByExample")
    int deleteByExample(ProductExample example);

    @Delete({
        "delete from product",
        "where productid = #{productid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer productid);

    @Insert({
        "insert into product (productid, productno, ",
        "catid, name, descn, ",
        "pic)",
        "values (#{productid,jdbcType=INTEGER}, #{productno,jdbcType=VARCHAR}, ",
        "#{catid,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, #{descn,jdbcType=CHAR}, ",
        "#{pic,jdbcType=CHAR})"
    })
    int insert(Product record);

    @InsertProvider(type=ProductSqlProvider.class, method="insertSelective")
    int insertSelective(Product record);

    @SelectProvider(type=ProductSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="productid", property="productid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="productno", property="productno", jdbcType=JdbcType.VARCHAR),
        @Result(column="catid", property="catid", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="descn", property="descn", jdbcType=JdbcType.CHAR),
        @Result(column="pic", property="pic", jdbcType=JdbcType.CHAR),
            @Result(property = "category" , column = "catid",
                    one = @One(select = "com.yesp.server.mapper.CategoryMapper.selectByPrimaryKey"))
    })
    List<Product> selectByExample(ProductExample example);

    @Select({
        "select",
        "productid, productno, catid, name, descn, pic",
        "from product",
        "where productid = #{productid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="productid", property="productid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="productno", property="productno", jdbcType=JdbcType.VARCHAR),
        @Result(column="catid", property="catid", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="descn", property="descn", jdbcType=JdbcType.CHAR),
        @Result(column="pic", property="pic", jdbcType=JdbcType.CHAR)
    })
    Product selectByPrimaryKey(Integer productid);

    @UpdateProvider(type=ProductSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

    @UpdateProvider(type=ProductSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    @UpdateProvider(type=ProductSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Product record);

    @Update({
        "update product",
        "set productno = #{productno,jdbcType=VARCHAR},",
          "catid = #{catid,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "descn = #{descn,jdbcType=CHAR},",
          "pic = #{pic,jdbcType=CHAR}",
        "where productid = #{productid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Product record);
}