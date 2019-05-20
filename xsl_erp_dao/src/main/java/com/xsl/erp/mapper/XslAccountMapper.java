package com.xsl.erp.mapper;


import xsl.erp.pojo.XslAccount;

import java.util.List;

public interface XslAccountMapper {
    int insert(XslAccount xslAccount);
    XslAccount selectLastOne();
    List<XslAccount> selectAll();
}
