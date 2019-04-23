package com.xsl.cms.mapper;

import org.apache.ibatis.annotations.Param;
import xsl.cms.pojo.XslDatetime;
import xsl.cms.pojo.XslDatetimeExample;

import java.util.List;

public interface XslExtendMapper {
    String getUserNameByHunterId(Integer hunterId);
    String getUserNameByMasterId(Integer masterId);
}