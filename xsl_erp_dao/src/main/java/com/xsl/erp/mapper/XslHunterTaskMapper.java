package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslHunterTask;
import xsl.erp.pojo.XslHunterTaskExample;

public interface XslHunterTaskMapper {
    long countByExample(XslHunterTaskExample example);

    int deleteByExample(XslHunterTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslHunterTask record);

    int insertSelective(XslHunterTask record);

    List<XslHunterTask> selectByExample(XslHunterTaskExample example);

    XslHunterTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslHunterTask record, @Param("example") XslHunterTaskExample example);

    int updateByExample(@Param("record") XslHunterTask record, @Param("example") XslHunterTaskExample example);

    int updateByPrimaryKeySelective(XslHunterTask record);

    int updateByPrimaryKey(XslHunterTask record);
}