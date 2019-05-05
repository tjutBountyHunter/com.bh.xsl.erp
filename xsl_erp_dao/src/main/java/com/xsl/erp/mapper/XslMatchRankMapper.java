package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslMatchRank;
import xsl.erp.pojo.XslMatchRankExample;

public interface XslMatchRankMapper {
    long countByExample(XslMatchRankExample example);

    int deleteByExample(XslMatchRankExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslMatchRank record);

    int insertSelective(XslMatchRank record);

    List<XslMatchRank> selectByExample(XslMatchRankExample example);

    XslMatchRank selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslMatchRank record, @Param("example") XslMatchRankExample example);

    int updateByExample(@Param("record") XslMatchRank record, @Param("example") XslMatchRankExample example);

    int updateByPrimaryKeySelective(XslMatchRank record);

    int updateByPrimaryKey(XslMatchRank record);
}