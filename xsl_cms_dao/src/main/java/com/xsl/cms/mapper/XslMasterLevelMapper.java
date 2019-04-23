package com.xsl.cms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.cms.pojo.XslMasterLevel;
import xsl.cms.pojo.XslMasterLevelExample;

public interface XslMasterLevelMapper {
    int countByExample(XslMasterLevelExample example);

    int deleteByExample(XslMasterLevelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslMasterLevel record);

    int insertSelective(XslMasterLevel record);

    List<XslMasterLevel> selectByExample(XslMasterLevelExample example);

    XslMasterLevel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslMasterLevel record, @Param("example") XslMasterLevelExample example);

    int updateByExample(@Param("record") XslMasterLevel record, @Param("example") XslMasterLevelExample example);

    int updateByPrimaryKeySelective(XslMasterLevel record);

    int updateByPrimaryKey(XslMasterLevel record);
}