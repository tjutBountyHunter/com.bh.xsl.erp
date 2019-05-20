package xsl.erp.service;

import xsl.erp.pojo.common.PageObject;

public interface XslAccountService {
   PageObject selectAll(Integer pageIndex, Integer pageSize);
}
