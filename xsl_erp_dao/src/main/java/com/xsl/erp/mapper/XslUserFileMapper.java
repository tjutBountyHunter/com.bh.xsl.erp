package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslUserFile;
import xsl.erp.pojo.XslUserFileExample;

public interface XslUserFileMapper {
    int countByExample(XslUserFileExample example);

    int deleteByExample(XslUserFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslUserFile record);

    int insertSelective(XslUserFile record);

    List<XslUserFile> selectByExample(XslUserFileExample example);

    XslUserFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslUserFile record, @Param("example") XslUserFileExample example);

    int updateByExample(@Param("record") XslUserFile record, @Param("example") XslUserFileExample example);

    int updateByPrimaryKeySelective(XslUserFile record);

    int updateByPrimaryKey(XslUserFile record);
}