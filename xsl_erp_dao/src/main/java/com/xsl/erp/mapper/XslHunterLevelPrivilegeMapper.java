package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslHunterLevelPrivilege;
import xsl.erp.pojo.XslHunterLevelPrivilegeExample;

public interface XslHunterLevelPrivilegeMapper {
    long countByExample(XslHunterLevelPrivilegeExample example);

    int deleteByExample(XslHunterLevelPrivilegeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslHunterLevelPrivilege record);

    int insertSelective(XslHunterLevelPrivilege record);

    List<XslHunterLevelPrivilege> selectByExample(XslHunterLevelPrivilegeExample example);

    XslHunterLevelPrivilege selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslHunterLevelPrivilege record, @Param("example") XslHunterLevelPrivilegeExample example);

    int updateByExample(@Param("record") XslHunterLevelPrivilege record, @Param("example") XslHunterLevelPrivilegeExample example);

    int updateByPrimaryKeySelective(XslHunterLevelPrivilege record);

    int updateByPrimaryKey(XslHunterLevelPrivilege record);
}