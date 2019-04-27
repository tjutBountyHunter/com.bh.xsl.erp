package xsl.erp.service;

import xsl.erp.commons.XslResult;
import xsl.erp.pojo.XslHunter;
import xsl.erp.pojo.common.PageObject;

import java.util.List;

//xsl猎人管理的service操作定义
public interface XslHunterService {
    PageObject selectHunterAll(Integer page, Integer rows, Short level);
    XslResult updateXslHunter(List<XslHunter> xslHunter);
}
