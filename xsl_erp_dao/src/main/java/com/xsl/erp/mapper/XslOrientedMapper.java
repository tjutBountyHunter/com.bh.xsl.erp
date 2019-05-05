package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslOriented;
import xsl.erp.pojo.XslOrientedExample;

public interface XslOrientedMapper {
    long countByExample(XslOrientedExample example);

    int deleteByExample(XslOrientedExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslOriented record);

    int insertSelective(XslOriented record);

    List<XslOriented> selectByExample(XslOrientedExample example);

    XslOriented selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslOriented record, @Param("example") XslOrientedExample example);

    int updateByExample(@Param("record") XslOriented record, @Param("example") XslOrientedExample example);

    int updateByPrimaryKeySelective(XslOriented record);

    int updateByPrimaryKey(XslOriented record);
}