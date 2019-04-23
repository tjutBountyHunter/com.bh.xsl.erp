package xsl.erp.service;

import xsl.erp.pojo.XslMasterTag;
import xsl.erp.pojo.common.PageObject;

/**
 * Master_label页面管理操作定义
 */
public interface XslMasterLabelService {
    PageObject SelectMasterLabelAll(Integer page, Integer rows, Integer key);
    boolean InsertXslMasterLabel(XslMasterTag[] xslMasterTags);
    boolean UpdateXslMasterLabel(XslMasterTag[] xslMasterTags);
    boolean deleteXslMasterLabel(XslMasterTag[] xslMasterTags);
}
