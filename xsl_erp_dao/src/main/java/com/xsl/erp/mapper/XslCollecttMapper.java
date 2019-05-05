package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslCollectt;
import xsl.erp.pojo.XslCollecttExample;

public interface XslCollecttMapper {
    long countByExample(XslCollecttExample example);

    int deleteByExample(XslCollecttExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslCollectt record);

    int insertSelective(XslCollectt record);

    List<XslCollectt> selectByExample(XslCollecttExample example);

    XslCollectt selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslCollectt record, @Param("example") XslCollecttExample example);

    int updateByExample(@Param("record") XslCollectt record, @Param("example") XslCollecttExample example);

    int updateByPrimaryKeySelective(XslCollectt record);

    int updateByPrimaryKey(XslCollectt record);
}