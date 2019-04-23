package xsl.erp.service;

import xsl.erp.pojo.XslMaster;
import xsl.erp.pojo.common.PageObject;

//xsl雇主管理的service操作定义
public interface XslMasterService {
    PageObject SelectMasterAll(Integer page, Integer rows, Integer key, Short key1);
    boolean UpdateXslMaster(XslMaster[] xslMasters);
}
