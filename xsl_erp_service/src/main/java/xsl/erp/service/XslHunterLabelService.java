package xsl.erp.service;

import xsl.erp.pojo.XslHunterTag;
import xsl.erp.pojo.common.PageObject;

/**
 * Hunter_label页面管理操作定义
 */
public interface XslHunterLabelService {
    PageObject SelectHunterLabelAll(Integer page, Integer rows, Integer key);
    boolean InsertXslHunterLabel(XslHunterTag[] xslHunterTags);
    boolean UpdateXslHunterLabel(XslHunterTag[] xslHunterTags);
    boolean deleteXslHunterLabel(XslHunterTag[] xslHunterTags);
}
