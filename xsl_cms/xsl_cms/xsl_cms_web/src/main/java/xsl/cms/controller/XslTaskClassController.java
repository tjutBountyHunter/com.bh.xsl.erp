package xsl.cms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.cms.annotation.SystemControllerLog;
import xsl.cms.commons.XslResult;
import xsl.cms.pojo.XslTaskCategory;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslTaskClassService;

/**
 *  对任务类别管理页面进行操作
 *  @author 王坤
 *  */
@Controller
@RequestMapping("/taskclass/show")
public class XslTaskClassController {
    /* 任务类别日志 */
    Logger logger = LoggerFactory.getLogger(XslTaskClassController.class);

    @Autowired
    private XslTaskClassService xslTaskClassService;

    //一定注意pageindex+1这个动作，因为第一页为0
    //@SystemControllerLog( description = "任务分类查询" )
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslTaskClassInfo(Integer pageIndex, Integer pageSize,Integer key,Integer key1){
        return this.xslTaskClassService.SelectTaskClassAll(pageIndex + 1,pageSize,key,key1);
    }

    /**
     * 批量添加
     * @param xslTaskCategories
     * @return 是否成功
     */
    @SystemControllerLog( description = "任务分类添加" )
    @RequestMapping("/add")
    @ResponseBody
    public XslResult insertXslTaskClass(@RequestBody XslTaskCategory[] xslTaskCategories){
        String tag = "任务分类添加";
        XslResult xslResult = null;
        if(xslTaskCategories != null){
            try{
                if(this.xslTaskClassService.InsertXslTaskClass(xslTaskCategories)){
                    xslResult = XslResult.build(200,tag + "成功!");
                }else {
                    xslResult = XslResult.build(200,tag + "失败!");
                }
            }catch (Exception e){
                logger.error(tag + "异常警报 :" + e.getMessage());
                xslResult = XslResult.build(400,tag + "异常警报!");
            }
        }
        return xslResult;//代表这失败
    }

    @SystemControllerLog( description = "任务分类更新" )
    @RequestMapping("/update")
    @ResponseBody
    public XslResult updateXslTaskClass(@RequestBody XslTaskCategory[] xslTaskCategories){
        String tag = "任务分类更新";
        XslResult xslResult = null;
        if(xslTaskCategories != null){
            try{
                if(this.xslTaskClassService.UpdateXslTaskClass(xslTaskCategories)){
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

    @SystemControllerLog( description = "任务分类删除" )
    @RequestMapping("/delete")
    @ResponseBody
    public XslResult deleteXslTask(@RequestBody XslTaskCategory[] xslTaskCategories){
        String tag = "任务分类删除";
        XslResult xslResult = null;
        if(xslTaskCategories != null){
            try {
                if(this.xslTaskClassService.deleteXslTaskClass(xslTaskCategories)){
                    xslResult = XslResult.build(200,tag + "成功!");
                }else {
                    xslResult = XslResult.build(200,tag + "失败!");
                }
            }catch (Exception e){
                logger.error(tag + "异常警报 :" + e.getMessage());
                xslResult = XslResult.build(400,tag + "异常警报!");
            }
        }
        return xslResult;
    }
}
