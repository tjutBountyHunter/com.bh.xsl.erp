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
import xsl.erp.pojo.XslMaster;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslMasterService;

/**
 *  对user_master.jsp进行操作
 *  @author 王坤
 *  */
@Controller
@RequestMapping("/user/master")
public class XslMasterController {
    /* 雇主日志 */
    Logger logger = LoggerFactory.getLogger(XslMasterController.class);

    @Autowired
    private XslMasterService xslMasterService;

    //一定注意pageindex+1这个动作，因为第一页为0
    //@SystemControllerLog( description = "雇主查询" )
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslXslMasterInfo(Integer pageIndex, Integer pageSize,Integer key,Short key1){
        return this.xslMasterService.SelectMasterAll(pageIndex+1,pageSize,key,key1);
    }

    @SystemControllerLog( description = "雇主更新" )
    @RequestMapping("/update")
    @ResponseBody
    public XslResult updateXslMaster(@RequestBody XslMaster[] xslMasters){
        String tag = "雇主更新";
        XslResult xslResult = null;
        if(xslMasters != null){
            try {
                if(this.xslMasterService.UpdateXslMaster(xslMasters)){
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
