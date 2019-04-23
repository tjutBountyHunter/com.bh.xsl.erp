package xsl.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslControllerLogService;

/**
 *  对controllerLog.jsp进行操作
 *  @author 王坤
 *  */
@Controller
@RequestMapping("/log/controller")
public class XslControllerLogController {
    @Autowired
    private XslControllerLogService xslControllerLogService;

    //一定注意pageindex+1这个动作，因为第一页为0
    //@SystemControllerLog( description = "Controller日志查询" )
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslControllerLogInfo(Integer pageIndex, Integer pageSize){
        return this.xslControllerLogService.SelectControllerLogAll(pageIndex+1,pageSize);
    }
}
