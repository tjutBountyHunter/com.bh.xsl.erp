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
import xsl.cms.pojo.XslHunterTag;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslHunterLabelService;

/**
 *  对client_label的一些增删查处理
 *  @author 王坤
 *  */
@Controller
@RequestMapping("/hunter/tag")
public class XslHunterLabelController {
    /* 猎人标签日志 */
    Logger logger = LoggerFactory.getLogger(XslHunterLabelController.class);

    @Autowired
    private XslHunterLabelService xslHunterLabelService;

    //一定注意pageindex+1这个动作，因为第一页为0
    //@SystemControllerLog( description = "猎人标签查询" )
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslHunterLabelInfo(Integer pageIndex, Integer pageSize,Integer key){
        return this.xslHunterLabelService.SelectHunterLabelAll(pageIndex + 1,pageSize,key);
    }

    /**
     * 批量添加
     * @param xslHunterTags
     * @return 是否成功
     */
    @SystemControllerLog( description = "猎人标签添加" )
    @RequestMapping("/add")
    @ResponseBody
    public XslResult insertXslHunterLabel(@RequestBody XslHunterTag[] xslHunterTags){
        String tag = "猎人标签添加";
        XslResult xslResult = null;
        if(xslHunterTags != null){
            try{
                if(this.xslHunterLabelService.InsertXslHunterLabel(xslHunterTags)){
                    xslResult = XslResult.build(200,tag + "成功!");
                }else{
                    xslResult = XslResult.build(200,tag + "失败!");
                }
            }catch (Exception e){
                logger.error(tag + "异常警报 :" + e.getMessage());
                xslResult = XslResult.build(400,tag + "异常警报!");
            }
        }
        return xslResult;//代表这失败
    }

    @SystemControllerLog( description = "猎人标签更新" )
    @RequestMapping("/update")
    @ResponseBody
    public XslResult updateXslHunterLabel(@RequestBody XslHunterTag[] xslHunterTags){
        String tag = "猎人标签更新";
        XslResult xslResult = null;
        if(xslHunterTags != null){
            try{
                if(this.xslHunterLabelService.UpdateXslHunterLabel(xslHunterTags)){
                    xslResult = XslResult.build(200,tag + "成功!");
                }else{
                    xslResult = XslResult.build(200,tag + "失败!");
                }
            }catch (Exception e){
                logger.error(tag + "异常报警 :" + e.getMessage());
                xslResult = XslResult.build(400,tag+ "异常警报!");
            }
        }
        return xslResult;
    }

    @SystemControllerLog( description = "猎人标签删除" )
    @RequestMapping("/delete")
    @ResponseBody
    public XslResult deleteXslHunterLabel(@RequestBody XslHunterTag[] xslHunterTags){
        String tag = "猎人标签删除";
        XslResult xslResult = null;
        if(xslHunterTags != null){
            try {
                if(this.xslHunterLabelService.deleteXslHunterLabel(xslHunterTags)){
                    xslResult = XslResult.build(200,tag + "成功!");
                }else{
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
