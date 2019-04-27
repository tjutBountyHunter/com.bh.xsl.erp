package xsl.erp.service;

import xsl.erp.commons.XslResult;
import xsl.erp.pojo.XslMaster;
import xsl.erp.pojo.common.PageObject;

import java.util.List;

//xsl雇主管理的service操作定义
public interface XslMasterService {
    PageObject selectMasterAll(Integer page, Integer rows, Short level);
    XslResult updateXslMaster(List<XslMaster> xslMasters);
}
