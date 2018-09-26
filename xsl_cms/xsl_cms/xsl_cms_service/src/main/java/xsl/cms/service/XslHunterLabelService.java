package xsl.cms.service;

import xsl.cms.pojo.XslHunterTag;
import xsl.cms.pojo.common.PageObject;

/**
 * Hunter_label页面管理操作定义
 */
public interface XslHunterLabelService {
    PageObject SelectHunterLabelAll(Integer page, Integer rows, Integer key);
    boolean InsertXslHunterLabel(XslHunterTag[] xslHunterTags);
    boolean UpdateXslHunterLabel(XslHunterTag[] xslHunterTags);
    boolean deleteXslHunterLabel(XslHunterTag[] xslHunterTags);
}
