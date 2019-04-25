package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslRewardRank;
import xsl.erp.pojo.XslRewardRankExample;

public interface XslRewardRankMapper {
    int countByExample(XslRewardRankExample example);

    int deleteByExample(XslRewardRankExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslRewardRank record);

    int insertSelective(XslRewardRank record);

    List<XslRewardRank> selectByExample(XslRewardRankExample example);

    XslRewardRank selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslRewardRank record, @Param("example") XslRewardRankExample example);

    int updateByExample(@Param("record") XslRewardRank record, @Param("example") XslRewardRankExample example);

    int updateByPrimaryKeySelective(XslRewardRank record);

    int updateByPrimaryKey(XslRewardRank record);
}