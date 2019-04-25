package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslPrivilegeRule;
import xsl.erp.pojo.XslPrivilegeRuleExample;

public interface XslPrivilegeRuleMapper {
    int countByExample(XslPrivilegeRuleExample example);

    int deleteByExample(XslPrivilegeRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslPrivilegeRule record);

    int insertSelective(XslPrivilegeRule record);

    List<XslPrivilegeRule> selectByExample(XslPrivilegeRuleExample example);

    XslPrivilegeRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslPrivilegeRule record, @Param("example") XslPrivilegeRuleExample example);

    int updateByExample(@Param("record") XslPrivilegeRule record, @Param("example") XslPrivilegeRuleExample example);

    int updateByPrimaryKeySelective(XslPrivilegeRule record);

    int updateByPrimaryKey(XslPrivilegeRule record);
}