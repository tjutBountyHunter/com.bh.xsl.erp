package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslHunterLevelRule;
import xsl.erp.pojo.XslHunterLevelRuleExample;

public interface XslHunterLevelRuleMapper {
    long countByExample(XslHunterLevelRuleExample example);

    int deleteByExample(XslHunterLevelRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslHunterLevelRule record);

    int insertSelective(XslHunterLevelRule record);

    List<XslHunterLevelRule> selectByExample(XslHunterLevelRuleExample example);

    XslHunterLevelRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslHunterLevelRule record, @Param("example") XslHunterLevelRuleExample example);

    int updateByExample(@Param("record") XslHunterLevelRule record, @Param("example") XslHunterLevelRuleExample example);

    int updateByPrimaryKeySelective(XslHunterLevelRule record);

    int updateByPrimaryKey(XslHunterLevelRule record);
}