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
import xsl.erp.pojo.XslHunter;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslHunterService;

import java.util.List;

/**
 *  对user_show.jsp进行操作
 *  */
@Controller
@RequestMapping("/user/hunter")
public class XslHunterController {
    /* 猎人日志 */
    Logger logger = LoggerFactory.getLogger(XslHunterController.class);

    @Autowired
    private XslHunterService xslHunterService;

    //一定注意pageindex+1这个动作，因为第一页为0
    //@SystemControllerLog( description = "猎人查询" )
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslHunterInfo(Integer pageIndex, Integer pageSize,Short level){
        return xslHunterService.selectHunterAll(pageIndex+1,pageSize, level);
    }


    @SystemControllerLog( description = "猎人更新" )
    @RequestMapping("/update")
    @ResponseBody
    public XslResult updateXslHunter(@RequestBody List<XslHunter> xslHunters){
        return xslHunterService.updateXslHunter(xslHunters);
    }

}
