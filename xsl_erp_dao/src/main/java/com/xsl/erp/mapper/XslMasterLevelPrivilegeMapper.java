package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslMasterLevelPrivilege;
import xsl.erp.pojo.XslMasterLevelPrivilegeExample;

public interface XslMasterLevelPrivilegeMapper {
    int countByExample(XslMasterLevelPrivilegeExample example);

    int deleteByExample(XslMasterLevelPrivilegeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslMasterLevelPrivilege record);

    int insertSelective(XslMasterLevelPrivilege record);

    List<XslMasterLevelPrivilege> selectByExample(XslMasterLevelPrivilegeExample example);

    XslMasterLevelPrivilege selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslMasterLevelPrivilege record, @Param("example") XslMasterLevelPrivilegeExample example);

    int updateByExample(@Param("record") XslMasterLevelPrivilege record, @Param("example") XslMasterLevelPrivilegeExample example);

    int updateByPrimaryKeySelective(XslMasterLevelPrivilege record);

    int updateByPrimaryKey(XslMasterLevelPrivilege record);
}