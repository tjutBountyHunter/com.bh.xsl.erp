package com.xsl.erp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslInputBill;
import xsl.erp.pojo.XslInputBillExample;

public interface XslInputBillMapper {
    int countByExample(XslInputBillExample example);

    int deleteByExample(XslInputBillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslInputBill record);

    int insertSelective(XslInputBill record);

    List<XslInputBill> selectByExample(XslInputBillExample example);

    XslInputBill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslInputBill record, @Param("example") XslInputBillExample example);

    int updateByExample(@Param("record") XslInputBill record, @Param("example") XslInputBillExample example);

    int updateByPrimaryKeySelective(XslInputBill record);

    int updateByPrimaryKey(XslInputBill record);
}