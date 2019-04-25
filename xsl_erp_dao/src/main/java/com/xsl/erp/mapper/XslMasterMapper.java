package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslMaster;
import xsl.erp.pojo.XslMasterExample;

public interface XslMasterMapper {
    int countByExample(XslMasterExample example);

    int deleteByExample(XslMasterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslMaster record);

    int insertSelective(XslMaster record);

    List<XslMaster> selectByExample(XslMasterExample example);

    XslMaster selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslMaster record, @Param("example") XslMasterExample example);

    int updateByExample(@Param("record") XslMaster record, @Param("example") XslMasterExample example);

    int updateByPrimaryKeySelective(XslMaster record);

    int updateByPrimaryKey(XslMaster record);

    int updateByMasterIdSelective(XslMaster record);
}