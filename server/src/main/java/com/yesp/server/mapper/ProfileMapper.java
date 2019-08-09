package com.yesp.server.mapper;

import com.yesp.server.model.Profile;
import com.yesp.server.model.ProfileExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface ProfileMapper {
    @SelectProvider(type=ProfileSqlProvider.class, method="countByExample")
    int countByExample(ProfileExample example);

    @DeleteProvider(type=ProfileSqlProvider.class, method="deleteByExample")
    int deleteByExample(ProfileExample example);

    @Delete({
        "delete from profile",
        "where userid = #{userid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer userid);

    @Insert({
        "insert into profile (userid, lang, ",
        "catid)",
        "values (#{userid,jdbcType=INTEGER}, #{lang,jdbcType=VARCHAR}, ",
        "#{catid,jdbcType=INTEGER})"
    })
    int insert(Profile record);

    @InsertProvider(type=ProfileSqlProvider.class, method="insertSelective")
    int insertSelective(Profile record);

    @SelectProvider(type=ProfileSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="userid", property="userid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="lang", property="lang", jdbcType=JdbcType.VARCHAR),
        @Result(column="catid", property="catid", jdbcType=JdbcType.INTEGER),
            @Result(property = "category", column = "catid" ,
                    one = @One(select = "com.yesp.server.mapper.CategoryMapper.selectByPrimaryKey"))
    })
    List<Profile> selectByExample(ProfileExample example);

    @Select({
        "select",
        "userid, lang, catid",
        "from profile",
        "where userid = #{userid,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="userid", property="userid", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="lang", property="lang", jdbcType=JdbcType.VARCHAR),
        @Result(column="catid", property="catid", jdbcType=JdbcType.INTEGER),
            @Result(property = "category", column = "catid" ,
                    one = @One(select = "com.yesp.server.mapper.CategoryMapper.selectByPrimaryKey"))
    })
    Profile selectByPrimaryKey(Integer userid);

    @UpdateProvider(type=ProfileSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Profile record, @Param("example") ProfileExample example);

    @UpdateProvider(type=ProfileSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Profile record, @Param("example") ProfileExample example);

    @UpdateProvider(type=ProfileSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Profile record);

    @Update({
        "update profile",
        "set lang = #{lang,jdbcType=VARCHAR},",
          "catid = #{catid,jdbcType=INTEGER}",
        "where userid = #{userid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Profile record);
}