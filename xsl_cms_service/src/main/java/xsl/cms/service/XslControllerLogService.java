package xsl.cms.service;

import xsl.cms.pojo.common.PageObject;

//Controller日志的查询
public interface XslControllerLogService {
    PageObject SelectControllerLogAll(Integer page, Integer rows);
}
