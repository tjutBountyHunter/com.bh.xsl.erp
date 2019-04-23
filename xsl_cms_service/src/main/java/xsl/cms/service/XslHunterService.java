package xsl.cms.service;

import xsl.cms.pojo.XslHunter;
import xsl.cms.pojo.common.PageObject;

//xsl猎人管理的service操作定义
public interface XslHunterService {
    PageObject SelectHunterAll(Integer page, Integer rows, Integer key, Short key1);
    boolean UpdateXslHunter(XslHunter[] xslHunter);
}
