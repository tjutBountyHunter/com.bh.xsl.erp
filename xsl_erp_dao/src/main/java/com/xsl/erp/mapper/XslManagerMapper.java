package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslManager;
import xsl.erp.pojo.XslManagerExample;

public interface XslManagerMapper {
    long countByExample(XslManagerExample example);

    int deleteByExample(XslManagerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslManager record);

    int insertSelective(XslManager record);

    List<XslManager> selectByExample(XslManagerExample example);

    XslManager selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslManager record, @Param("example") XslManagerExample example);

    int updateByExample(@Param("record") XslManager record, @Param("example") XslManagerExample example);

    int updateByPrimaryKeySelective(XslManager record);

    int updateByPrimaryKey(XslManager record);
}