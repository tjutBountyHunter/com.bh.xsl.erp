package com.xsl.cms.mapper;

import org.apache.ibatis.annotations.Param;
import xsl.cms.pojo.XslOutputBill;
import xsl.cms.pojo.XslOutputBillExample;

import java.util.List;

public interface XslOutputBillMapper {
    int countByExample(XslOutputBillExample example);

    int deleteByExample(XslOutputBillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslOutputBill record);

    int insertSelective(XslOutputBill record);

    List<XslOutputBill> selectByExample(XslOutputBillExample example);

    XslOutputBill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslOutputBill record, @Param("example") XslOutputBillExample example);

    int updateByExample(@Param("record") XslOutputBill record, @Param("example") XslOutputBillExample example);

    int updateByPrimaryKeySelective(XslOutputBill record);

    int updateByPrimaryKey(XslOutputBill record);
}