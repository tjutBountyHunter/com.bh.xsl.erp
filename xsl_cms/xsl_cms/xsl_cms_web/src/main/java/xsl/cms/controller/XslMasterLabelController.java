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
import xsl.cms.pojo.XslMasterTag;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslMasterLabelService;

/**
 *  对client_label的一些增删查处理
 *  @author 王坤
 *  */
@Controller
@RequestMapping("/master/tag")
public class XslMasterLabelController {
    /* 雇主标签日志 */
    Logger logger = LoggerFactory.getLogger(XslMasterLabelController.class);

    @Autowired
    private XslMasterLabelService xslMasterLabelService;

    //一定注意pageindex+1这个动作，因为第一页为0
    //@SystemControllerLog( description = "雇主标签查询" )
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslMasterLabelInfo(Integer pageIndex, Integer pageSize,Integer key){
        return this.xslMasterLabelService.SelectMasterLabelAll(pageIndex + 1,pageSize,key);
    }

    /**
     * 批量添加
     * @param xslMasterTags
     * @return 是否成功
     */
    @SystemControllerLog( description = "雇主标签添加" )
    @RequestMapping("/add")
    @ResponseBody
    public XslResult insertXslMasterLabel(@RequestBody XslMasterTag[] xslMasterTags){
        String tag = "雇主标签添加";
        XslResult xslResult = null;
        if(xslMasterTags != null){
            try{
                if(this.xslMasterLabelService.InsertXslMasterLabel(xslMasterTags)){
                    xslResult = XslResult.build(200,tag + "成功!");
                }else{
                    xslResult = XslResult.build(200,tag + "失败!");
                }
            }catch (Exception e){
                logger.error(tag + "异常警报  :" + e.getMessage());
                xslResult = XslResult.build(400,tag + "异常警报!");
            }
        }
        return xslResult;//代表这失败
    }

    @SystemControllerLog( description = "雇主标签更新" )
    @RequestMapping("/update")
    @ResponseBody
    public XslResult updateXslMasterLabel(@RequestBody XslMasterTag[] xslMasterTags){
        String tag = "雇主标签更新";
        XslResult xslResult = null;
        if(xslMasterTags != null){
            try{
                if (this.xslMasterLabelService.UpdateXslMasterLabel(xslMasterTags)) {
                    xslResult = XslResult.build(200,tag + "成功!");
                }else{
                    xslResult = XslResult.build(200,tag + "失败!");
                }
            }catch (Exception e){
                logger.error(tag + "异常警报  :" + e.getMessage());
                xslResult = XslResult.build(400,"异常警报!");
            }
        }
        return xslResult;
    }

    @SystemControllerLog( description = "雇主标签删除" )
    @RequestMapping("/delete")
    @ResponseBody
    public XslResult deleteXslMasterLabel(@RequestBody XslMasterTag[] xslMasterTags){
        String tag = "雇主标签删除";
        XslResult xslResult = null;
        if(xslMasterTags != null){
            try {
                if(this.xslMasterLabelService.deleteXslMasterLabel(xslMasterTags)){
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