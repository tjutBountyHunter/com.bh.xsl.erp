package com.xsl.erp.mapper;

import xsl.erp.pojo.XslPush;

import java.util.List;

public interface XslPushMapper {
    List<XslPush> selectAll();
    int addPush(XslPush xslPush);
    int alterPush(XslPush xslPush);
    int deletePush(XslPush xslPush);

}
