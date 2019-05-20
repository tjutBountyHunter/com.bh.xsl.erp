package xsl.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslAccountService;

@Controller
@RequestMapping("/account/show")
public class XslAccountController {
    @Autowired
    private XslAccountService xslAccountService;
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslUserInfo(Integer pageIndex, Integer pageSize){
        return xslAccountService.selectAll(pageIndex + 1,pageSize);
    }


}
