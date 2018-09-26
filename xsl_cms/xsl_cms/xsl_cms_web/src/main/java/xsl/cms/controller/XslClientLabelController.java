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
import xsl.cms.pojo.XslTag;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslClientLabelService;

/**
 *  对client_label的一些增删查处理
 *  @author 王坤
 *  */
@Controller
@RequestMapping("/client/tag")
public class XslClientLabelController {
    /* 标签日志 */
    Logger logger = LoggerFactory.getLogger(XslClientLabelController.class);

    @Autowired
    private XslClientLabelService xslClientLabelService;

    //一定注意pageindex+1这个动作，因为第一页为0
    //@SystemControllerLog( description = "标签查询" )
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslClientLabelInfo(Integer pageIndex, Integer pageSize,Integer key){
        return this.xslClientLabelService.SelectClientLabelAll(pageIndex + 1,pageSize,key);
    }

    /**
     * 批量添加
     * @param xslTags
     * @return 是否成功
     */
    @SystemControllerLog( description = "标签添加" )
    @RequestMapping("/add")
    @ResponseBody
    public XslResult insertXslClientLabel(@RequestBody XslTag[] xslTags){
        String tag = "标签添加";
        XslResult xslResult = null;
        if(xslTags != null){
            try {
                if(this.xslClientLabelService.InsertXslClientLabel(xslTags)){
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

    @SystemControllerLog( description = "标签更新" )
    @RequestMapping("/update")
    @ResponseBody
    public XslResult updateXslClientLabel(@RequestBody XslTag[] xslTags){
        String tag = "标签更新";
        XslResult xslResult = null;
        if(xslTags != null){
            try{
                if(this.xslClientLabelService.UpdateXslClientLabel(xslTags)){
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

    @SystemControllerLog( description = "标签删除" )
    @RequestMapping("/delete")
    @ResponseBody
    public XslResult deleteXslClientLabel(@RequestBody XslTag[] xslTags){
        String tag = "标签删除";
        XslResult xslResult = null;
        if(xslTags != null){
            try{
                if(this.xslClientLabelService.deleteXslClientLabel(xslTags)){
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