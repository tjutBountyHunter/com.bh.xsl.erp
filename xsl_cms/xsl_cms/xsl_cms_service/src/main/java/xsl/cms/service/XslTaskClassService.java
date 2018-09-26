package xsl.cms.service;

import xsl.cms.pojo.XslTaskCategory;
import xsl.cms.pojo.common.MonitorNode;
import xsl.cms.pojo.common.PageObject;

import java.util.List;

/**
 * xsl任务类别的service操作定义
 */
public interface XslTaskClassService {
    PageObject SelectTaskClassAll(Integer page, Integer rows, Integer key, Integer key1);
    boolean InsertXslTaskClass(XslTaskCategory[] xslTaskCategories);
    boolean UpdateXslTaskClass(XslTaskCategory[] xslTaskCategories);
    boolean deleteXslTaskClass(XslTaskCategory[] xslTaskCategories);
    //*********monitor监控任务的类别
    List<MonitorNode> monitorTaskClassCount();
}
