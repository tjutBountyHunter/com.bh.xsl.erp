package xsl.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.erp.pojo.common.MonitorNode;
import xsl.erp.service.XslClientLabelService;

import java.util.List;

/**
 * monitor_Tag监控标签
 */
@Controller
@RequestMapping("/monitor/tag")
public class MonitorTagount {

    @Autowired
    private XslClientLabelService xslClientLabelService;

    @RequestMapping("/mount")
    @ResponseBody
    public List<MonitorNode> getUserCount(){
        return this.xslClientLabelService.getTagCount();
    }
}
