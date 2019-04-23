package xsl.cms.service;

import xsl.cms.pojo.XslMasterTag;
import xsl.cms.pojo.common.PageObject;

/**
 * Master_label页面管理操作定义
 */
public interface XslMasterLabelService {
    PageObject SelectMasterLabelAll(Integer page, Integer rows, Integer key);
    boolean InsertXslMasterLabel(XslMasterTag[] xslMasterTags);
    boolean UpdateXslMasterLabel(XslMasterTag[] xslMasterTags);
    boolean deleteXslMasterLabel(XslMasterTag[] xslMasterTags);
}
