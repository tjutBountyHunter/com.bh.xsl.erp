package xsl.erp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.erp.annotation.SystemControllerLog;
import xsl.erp.pojo.XslUser;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslUserService;

/**
 *  对user_approve.jsp进行操作,服务层在user_showService中
 *
 *  @author 王坤
 *  */
@Controller
@RequestMapping("/user/approve")
public class XslUserApproveController {
    /* 用户审核日志 */
    Logger logger = LoggerFactory.getLogger(XslUserApproveController.class);

    @Autowired
    private XslUserService xslUserService;

    //pageIndex+1不写的话，如果只有一页的话，是显示不出来的。
    //@SystemControllerLog( description = "用户审核查询" )
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslUserInfo(Integer pageIndex, Integer pageSize){
        return this.xslUserService.SelectUserApprove(pageIndex+1,pageSize);
    }

    //没有requestBody是不能讲
    @SystemControllerLog( description = "用户审核操作" )
    @RequestMapping("/approve")
    @ResponseBody
    public boolean approve(@RequestBody XslUser xslUser){
        try {
            return this.xslUserService.approve(xslUser);
        }catch (Exception e){
            logger.error("用户审核异常报警  :" + e.getMessage());
            return false;
        }
    }
}
