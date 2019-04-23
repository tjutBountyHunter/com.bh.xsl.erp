package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslHistoryh;
import xsl.erp.pojo.XslHistoryhExample;

public interface XslHistoryhMapper {
    int countByExample(XslHistoryhExample example);

    int deleteByExample(XslHistoryhExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslHistoryh record);

    int insertSelective(XslHistoryh record);

    List<XslHistoryh> selectByExample(XslHistoryhExample example);

    XslHistoryh selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslHistoryh record, @Param("example") XslHistoryhExample example);

    int updateByExample(@Param("record") XslHistoryh record, @Param("example") XslHistoryhExample example);

    int updateByPrimaryKeySelective(XslHistoryh record);

    int updateByPrimaryKey(XslHistoryh record);
}