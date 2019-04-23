package xsl.erp.service;

import xsl.erp.pojo.XslTask;
import xsl.erp.pojo.common.PageObject;

/**
 * xsl任务的service操作定义
 */
public interface XslTaskService {
    PageObject SelectTaskAll(Integer page, Integer rows, Integer key, Byte key1);
    boolean InsertXslTask(XslTask[] xslTasks);
    boolean UpdateXslTask(XslTask[] xslTasks);
    boolean deleteXslTask(XslTask[] xslTasks);
    boolean delXslTask(XslTask xslTask);
    //********task_approve的两个操作
    PageObject SelectTaskApprove(Integer page, Integer rows);
    boolean approve(XslTask xslTask);

    //**************monitor 监控任务数量变化
    Integer taskWaitSum();
    Integer taskingSum();
    Integer taskSuccessSum();
    Integer taskFailSum();
}
