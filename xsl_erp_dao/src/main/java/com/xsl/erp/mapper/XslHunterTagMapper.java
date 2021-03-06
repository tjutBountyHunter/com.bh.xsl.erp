package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslHunterTag;
import xsl.erp.pojo.XslHunterTagExample;

public interface XslHunterTagMapper {
    long countByExample(XslHunterTagExample example);

    int deleteByExample(XslHunterTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslHunterTag record);

    int insertSelective(XslHunterTag record);

    List<XslHunterTag> selectByExample(XslHunterTagExample example);

    XslHunterTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslHunterTag record, @Param("example") XslHunterTagExample example);

    int updateByExample(@Param("record") XslHunterTag record, @Param("example") XslHunterTagExample example);

    int updateByPrimaryKeySelective(XslHunterTag record);

    int updateByPrimaryKey(XslHunterTag record);
}