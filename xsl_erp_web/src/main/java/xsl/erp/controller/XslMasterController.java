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

import java.util.List;

/**
 *  对user_master.jsp进行操作
 *  */
@Controller
@RequestMapping("/user/master")
public class XslMasterController {
    /* 雇主日志 */
    Logger logger = LoggerFactory.getLogger(XslMasterController.class);

    @Autowired
    private XslMasterService xslMasterService;

    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslXslMasterInfo(Integer pageIndex, Integer pageSize, Short level){
        return xslMasterService.selectMasterAll(pageIndex+1,pageSize,level);
    }

    @SystemControllerLog( description = "雇主更新" )
    @RequestMapping("/update")
    @ResponseBody
    public XslResult updateXslMaster(@RequestBody List<XslMaster> xslMasters){
        return xslMasterService.updateXslMaster(xslMasters);
    }
}
