package xsl.cms.service;

import xsl.cms.pojo.XslMaster;
import xsl.cms.pojo.common.PageObject;

//xsl雇主管理的service操作定义
public interface XslMasterService {
    PageObject SelectMasterAll(Integer page, Integer rows, Integer key, Short key1);
    boolean UpdateXslMaster(XslMaster[] xslMasters);
}
