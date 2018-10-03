package com.xsl.cms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.cms.pojo.XslCode;
import xsl.cms.pojo.XslCodeExample;

public interface XslCodeMapper {
    int countByExample(XslCodeExample example);

    int deleteByExample(XslCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslCode record);

    int insertSelective(XslCode record);

    List<XslCode> selectByExample(XslCodeExample example);

    XslCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslCode record, @Param("example") XslCodeExample example);

    int updateByExample(@Param("record") XslCode record, @Param("example") XslCodeExample example);

    int updateByPrimaryKeySelective(XslCode record);

    int updateByPrimaryKey(XslCode record);
}