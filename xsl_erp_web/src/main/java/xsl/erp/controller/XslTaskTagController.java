package xsl.erp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.erp.annotation.SystemControllerLog;
import xsl.erp.commons.XslResult;
import xsl.erp.pojo.XslTaskTag;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslTaskTagService;

/**
 *  对任务标签管理页面进行操作task_tag
 *  @author 王坤
 *  */
@Controller
@RequestMapping("/task/tag")
public class XslTaskTagController {
    /* 任务标签日志 */
    private Logger logger = LoggerFactory.getLogger(XslTaskTagController.class);

    @Autowired
    private XslTaskTagService xslTaskTagService;

    //一定注意pageindex+1这个动作，因为第一页为0
    //@SystemControllerLog( description = "任务标签查询" )
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslTaskTagInfo(Integer pageIndex, Integer pageSize,Integer key,Integer key1){
        return this.xslTaskTagService.SelectTaskTagAll(pageIndex + 1,pageSize,key,key1);
    }

    /**
     * 批量添加
     * @param xslTaskTags
     * @return 是否成功
     */
    @SystemControllerLog( description = "任务标签添加" )
    @RequestMapping("/add")
    @ResponseBody
    public XslResult insertXslTaskTag(@RequestBody XslTaskTag[] xslTaskTags){
        String tag = "任务标签添加";
        XslResult xslResult = null;
        if(xslTaskTags != null){
            try {
                if(this.xslTaskTagService.InsertXslTaskTag(xslTaskTags)){
                    xslResult = XslResult.build(200,tag + "成功!");
                }else {
                    xslResult = XslResult.build(200,tag + "失败!");
                }
            }catch (Exception e){
                logger.error(tag + "异常报警 :" + e.getMessage());
                xslResult = XslResult.build(200,tag + "异常警报!");
            }
        }
        return xslResult;//代表这失败
    }

    @SystemControllerLog( description = "任务标签更新" )
    @RequestMapping("/update")
    @ResponseBody
    public XslResult updateXslTaskTag(@RequestBody XslTaskTag[] xslTaskTags){
        String tag = "任务标签更新";
        XslResult xslResult = null;
        if(xslTaskTags != null){
            try{
                if(this.xslTaskTagService.UpdateXslTaskTag(xslTaskTags)){
                    xslResult = XslResult.build(200,tag + "成功!");
                }else{
                    xslResult = XslResult.build(200,tag + "失败!");
                }
            }catch (Exception e){
                logger.error(tag + "异常报警  :" + e.getMessage());
                xslResult = XslResult.build(400,tag + "异常报警!");
            }
        }
        return xslResult;
    }

    @SystemControllerLog( description = "任务标签删除" )
    @RequestMapping("/delete")
    @ResponseBody
    public XslResult deleteXslTask(@RequestBody XslTaskTag[] xslTaskTags){
        String tag = "任务标签删除";
        XslResult xslResult = null;
        if(xslTaskTags != null){
            try {
                if( this.xslTaskTagService.deleteXslTaskTag(xslTaskTags) ){
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
}