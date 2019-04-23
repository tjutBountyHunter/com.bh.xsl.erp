package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslToken;
import xsl.erp.pojo.XslTokenExample;

public interface XslTokenMapper {
    int countByExample(XslTokenExample example);

    int deleteByExample(XslTokenExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslToken record);

    int insertSelective(XslToken record);

    List<XslToken> selectByExample(XslTokenExample example);

    XslToken selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslToken record, @Param("example") XslTokenExample example);

    int updateByExample(@Param("record") XslToken record, @Param("example") XslTokenExample example);

    int updateByPrimaryKeySelective(XslToken record);

    int updateByPrimaryKey(XslToken record);
}