package com.xsl.cms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.cms.pojo.XslCollege;
import xsl.cms.pojo.XslCollegeExample;

public interface XslCollegeMapper {
    int countByExample(XslCollegeExample example);

    int deleteByExample(XslCollegeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslCollege record);

    int insertSelective(XslCollege record);

    List<XslCollege> selectByExample(XslCollegeExample example);

    XslCollege selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslCollege record, @Param("example") XslCollegeExample example);

    int updateByExample(@Param("record") XslCollege record, @Param("example") XslCollegeExample example);

    int updateByPrimaryKeySelective(XslCollege record);

    int updateByPrimaryKey(XslCollege record);
}