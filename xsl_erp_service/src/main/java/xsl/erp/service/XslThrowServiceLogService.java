package xsl.erp.service;

import xsl.erp.pojo.common.PageObject;

//Controller日志的查询
public interface XslThrowServiceLogService {
    PageObject SelectThrowServiceLogAll(Integer page, Integer rows);
}
