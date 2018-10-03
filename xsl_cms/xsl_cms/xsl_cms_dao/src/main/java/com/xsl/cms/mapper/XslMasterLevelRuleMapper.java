package com.xsl.cms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.cms.pojo.XslMasterLevelRule;
import xsl.cms.pojo.XslMasterLevelRuleExample;

public interface XslMasterLevelRuleMapper {
    int countByExample(XslMasterLevelRuleExample example);

    int deleteByExample(XslMasterLevelRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslMasterLevelRule record);

    int insertSelective(XslMasterLevelRule record);

    List<XslMasterLevelRule> selectByExample(XslMasterLevelRuleExample example);

    XslMasterLevelRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslMasterLevelRule record, @Param("example") XslMasterLevelRuleExample example);

    int updateByExample(@Param("record") XslMasterLevelRule record, @Param("example") XslMasterLevelRuleExample example);

    int updateByPrimaryKeySelective(XslMasterLevelRule record);

    int updateByPrimaryKey(XslMasterLevelRule record);
}