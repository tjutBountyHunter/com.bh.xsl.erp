package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslManagerRole;
import xsl.erp.pojo.XslManagerRoleExample;

public interface XslManagerRoleMapper {
    long countByExample(XslManagerRoleExample example);

    int deleteByExample(XslManagerRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslManagerRole record);

    int insertSelective(XslManagerRole record);

    List<XslManagerRole> selectByExample(XslManagerRoleExample example);

    XslManagerRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslManagerRole record, @Param("example") XslManagerRoleExample example);

    int updateByExample(@Param("record") XslManagerRole record, @Param("example") XslManagerRoleExample example);

    int updateByPrimaryKeySelective(XslManagerRole record);

    int updateByPrimaryKey(XslManagerRole record);
}