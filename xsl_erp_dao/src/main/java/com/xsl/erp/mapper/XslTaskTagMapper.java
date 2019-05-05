package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslTaskTag;
import xsl.erp.pojo.XslTaskTagExample;

public interface XslTaskTagMapper {
    long countByExample(XslTaskTagExample example);

    int deleteByExample(XslTaskTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslTaskTag record);

    int insertSelective(XslTaskTag record);

    List<XslTaskTag> selectByExample(XslTaskTagExample example);

    XslTaskTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslTaskTag record, @Param("example") XslTaskTagExample example);

    int updateByExample(@Param("record") XslTaskTag record, @Param("example") XslTaskTagExample example);

    int updateByPrimaryKeySelective(XslTaskTag record);

    int updateByPrimaryKey(XslTaskTag record);
}