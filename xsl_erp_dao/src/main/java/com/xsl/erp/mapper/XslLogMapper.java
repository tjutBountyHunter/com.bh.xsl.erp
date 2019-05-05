package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslLog;
import xsl.erp.pojo.XslLogExample;

public interface XslLogMapper {
    long countByExample(XslLogExample example);

    int deleteByExample(XslLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslLog record);

    int insertSelective(XslLog record);

    List<XslLog> selectByExample(XslLogExample example);

    XslLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslLog record, @Param("example") XslLogExample example);

    int updateByExample(@Param("record") XslLog record, @Param("example") XslLogExample example);

    int updateByPrimaryKeySelective(XslLog record);

    int updateByPrimaryKey(XslLog record);
}