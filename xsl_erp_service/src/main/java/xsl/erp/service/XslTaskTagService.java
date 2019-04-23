package xsl.erp.service;

import xsl.erp.pojo.XslTaskTag;
import xsl.erp.pojo.common.PageObject;

/**
 * xsl任务标签的service操作定义
 */
public interface XslTaskTagService {
    PageObject SelectTaskTagAll(Integer page, Integer rows, Integer key, Integer key1);
    boolean InsertXslTaskTag(XslTaskTag[] xslTaskTags);
    boolean UpdateXslTaskTag(XslTaskTag[] xslTaskTags);
    boolean deleteXslTaskTag(XslTaskTag[] xslTaskTags);
}
