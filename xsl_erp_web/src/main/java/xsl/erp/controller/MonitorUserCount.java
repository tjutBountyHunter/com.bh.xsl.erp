package xsl.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.erp.service.XslUserService;

@Controller
@RequestMapping("/monitor/user")
public class MonitorUserCount {

    @Autowired
    private XslUserService xslUserService;

    @RequestMapping("/mount")
    @ResponseBody
    public Integer getUserCount(){
        return this.xslUserService.countUser();
    }
}
