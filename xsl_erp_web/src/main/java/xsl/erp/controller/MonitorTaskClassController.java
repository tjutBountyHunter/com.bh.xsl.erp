package xsl.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.erp.pojo.common.MonitorNode;
import xsl.erp.service.XslTaskClassService;

import java.util.List;

/**
 * 任务监控
 * @author  王坤
 */
@Controller
@RequestMapping("/monitor")
public class MonitorTaskClassController {

    @Autowired
    private XslTaskClassService xslTaskClassService;

    @RequestMapping("/taskClassCount")
    @ResponseBody
    public List<MonitorNode> getTaskClassCount(){
        return this.xslTaskClassService.monitorTaskClassCount();
    }
}
