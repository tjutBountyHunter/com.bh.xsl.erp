package xsl.cms.service;

import xsl.cms.pojo.XslTag;
import xsl.cms.pojo.common.MonitorNode;
import xsl.cms.pojo.common.PageObject;

import java.util.List;

/**
 * xsl客户端标签管理操作定义
 */
public interface XslClientLabelService {
    PageObject SelectClientLabelAll(Integer page, Integer rows, Integer key);
    boolean InsertXslClientLabel(XslTag[] xslTags);
    boolean UpdateXslClientLabel(XslTag[] xslTags);
    boolean deleteXslClientLabel(XslTag[] xslTags);
    //**********监控标签
    List<MonitorNode> getTagCount();
}
