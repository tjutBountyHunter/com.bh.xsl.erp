package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslTag;
import xsl.erp.pojo.XslTagExample;

public interface XslTagMapper {
    long countByExample(XslTagExample example);

    int deleteByExample(XslTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslTag record);

    int insertSelective(XslTag record);

    List<XslTag> selectByExample(XslTagExample example);

    XslTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslTag record, @Param("example") XslTagExample example);

    int updateByExample(@Param("record") XslTag record, @Param("example") XslTagExample example);

    int updateByPrimaryKeySelective(XslTag record);

    int updateByPrimaryKey(XslTag record);
}