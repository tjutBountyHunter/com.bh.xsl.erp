package xsl.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslThrowServiceLogService;

/**
 *  对controllerLog.jsp进行操作
 *  @author 王坤
 *  */
@Controller
@RequestMapping("/log/service")
public class XslThrowServiceLogController {
    @Autowired
    private XslThrowServiceLogService xslThrowServiceLogService;

    //一定注意pageindex+1这个动作，因为第一页为0
    //@SystemControllerLog( description = "Service异常日志查询" )
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslThrowServiceInfo(Integer pageIndex, Integer pageSize){
        return this.xslThrowServiceLogService.SelectThrowServiceLogAll(pageIndex+1,pageSize);
    }
}
