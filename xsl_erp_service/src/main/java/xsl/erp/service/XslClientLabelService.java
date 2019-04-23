package xsl.erp.service;

import xsl.erp.pojo.XslTag;
import xsl.erp.pojo.common.MonitorNode;
import xsl.erp.pojo.common.PageObject;

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
