package xsl.erp.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vo.PageObject;
import xsl.erp.annotation.SystemControllerLog;
import xsl.erp.commons.XslResult;
import xsl.erp.pojo.XslTask;
import xsl.erp.service.XslTaskService;

/**
 *  对xsl_tasl.jsp进行操作
 *  @author 王坤
 *  */
@Controller
@RequestMapping("/task/show")
public class XslTaskController {
    /* 任务日志 */
    org.slf4j.Logger logger = LoggerFactory.getLogger(XslTaskController.class);

    @Autowired
    private XslTaskService xslTaskService;

    //一定注意pageindex+1这个动作，因为第一页为0
    //@SystemControllerLog( description = "任务查询" )
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslTaskInfo(Integer pageIndex, Integer pageSize, Integer key, Byte key1){
        return xslTaskService.SelectTaskAll(pageIndex + 1,pageSize,key,key1);
    }

    /**
     * 批量添加
     * @param xslTasks
     * @return 是否成功
     */
    @SystemControllerLog( description = "任务添加" )
    @RequestMapping("/add")
    @ResponseBody
    public XslResult insertXslTask(@RequestBody XslTask[] xslTasks){
        String tag = "任务添加";
        XslResult xslResult = null;
        if(xslTasks != null){
            try{
                if(this.xslTaskService.InsertXslTask(xslTasks)){
                    xslResult = XslResult.build(200,tag + "成功!");
                }else {
                    xslResult = XslResult.build(200,tag + "失败!");
                }
            }catch (Exception e){
                logger.error(tag + "异常警报  :" + e.getMessage());
                xslResult = XslResult.build(400,tag + "异常警报!");
            }
        }
        return xslResult;//代表这失败
    }

    @SystemControllerLog( description = "任务更新" )
    @RequestMapping("/update")
    @ResponseBody
    public XslResult updateXslTask(@RequestBody XslTask[] xslTasks){
        String tag = "任务更新";
        XslResult xslResult = null;
        if(xslTasks != null){
            try {
                if(this.xslTaskService.UpdateXslTask(xslTasks)){
                    xslResult = XslResult.build(200,tag + "成功!");
                }else {
                    xslResult = XslResult.build(200,tag + "失败!");
                }
            }catch (Exception e){
                logger.error(tag + "异常报警  :" + e.getMessage());
                xslResult = XslResult.build(400,tag + "异常报警!");
            }
        }
        return xslResult;
    }

    @SystemControllerLog( description = "任务删除" )
    @RequestMapping("/delete")
    @ResponseBody
    public XslResult deleteXslTask(@RequestBody XslTask[] xslTasks){
        String tag = "任务删除";
        XslResult xslResult = null;
        if(xslTasks != null){
            try{
                if(this.xslTaskService.deleteXslTask(xslTasks)){
                    xslResult = XslResult.build(200,tag + "成功!");
                }else {
                    xslResult = XslResult.build(200,tag + "失败!");
                }
            }catch (Exception e){
                logger.error(tag + "异常警报  :" + e.getMessage());
                xslResult = XslResult.build(400,tag + "异常警报!");
            }
        }
        return xslResult;
    }
}
