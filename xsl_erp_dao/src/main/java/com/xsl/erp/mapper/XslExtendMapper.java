package com.xsl.erp.mapper;

import org.apache.ibatis.annotations.Param;
import xsl.erp.pojo.XslDatetime;
import xsl.erp.pojo.XslDatetimeExample;

import java.util.List;

public interface XslExtendMapper {
    String getUserNameByHunterId(Integer hunterId);
    String getUserNameByMasterId(Integer masterId);
}