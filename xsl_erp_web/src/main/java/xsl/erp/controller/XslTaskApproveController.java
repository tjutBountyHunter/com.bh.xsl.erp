package xsl.erp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vo.PageObject;
import xsl.erp.annotation.SystemControllerLog;
import xsl.erp.pojo.XslTask;
import xsl.erp.service.XslTaskService;

/**
 *  对task_approve.jsp进行操作,服务层在task_showService中
 *  @author 王坤
 *  */
@Controller
@RequestMapping("/task/approve")
public class XslTaskApproveController {
    /* 任务审核日志 */
    Logger logger = LoggerFactory.getLogger(XslTaskApproveController.class);

    @Autowired
    private XslTaskService xslTaskService;

    //pageIndex+1不写的话，如果只有一页的话，是显示不出来的。
    //@SystemControllerLog( description = "任务审核查询" )
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslTaskInfo(Integer pageIndex, Integer pageSize){
        return xslTaskService.SelectTaskApprove(pageIndex+1,pageSize);
    }

    //没有requestBody是不能讲
    @SystemControllerLog( description = "任务审核操作" )
    @RequestMapping("/approve")
    @ResponseBody
    public boolean approve(@RequestBody XslTask xslTask){
        String tag = "任务审核操作";
        try{
            return this.xslTaskService.approve(xslTask);
        }catch (Exception e){
            logger.error(tag + "异常警报  :" + e.getMessage());
            return false;
        }
    }
}
