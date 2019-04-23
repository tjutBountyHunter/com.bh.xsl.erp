package xsl.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.erp.pojo.common.MonitorNode;
import xsl.erp.service.XslTaskService;

import java.util.ArrayList;

/**
 * 任务监控
 * @author  王坤
 */
@Controller
@RequestMapping("/monitor")
public class MonitorTaskCountController {
    @Autowired
    private XslTaskService xslTaskService;


    @RequestMapping("/taskWaitSum")
    @ResponseBody
    public Integer getTaskWaitSum(){
        return this.xslTaskService.taskWaitSum();
    }

    @RequestMapping("/taskingSum")
    @ResponseBody
    public Integer getTaskingSum(){
        return this.xslTaskService.taskingSum();
    }

    @RequestMapping("/taskSuccessSum")
    @ResponseBody
    public Integer getTaskSuccessSum(){
        return this.xslTaskService.taskSuccessSum();
    }

    @RequestMapping("/taskFailSum")
    @ResponseBody
    public Integer getTaskFailSum(){
        return this.xslTaskService.taskFailSum();
    }

    /**
     * 第一个值表示任务总数量(待接收和进行中)
     * 第二个值表示任务进行中的数量
     * @return
     */
    @RequestMapping("/sum")
    @ResponseBody
    public Integer[] getTaskSum_TaskingSum(){
        Integer taskingSum = getTaskingSum();
        Integer taskSum = getTaskWaitSum() + taskingSum;
        Integer[] sums = {taskSum,taskingSum};
        return sums;
    }

    /**
     *  任务完成度
     * @author 王坤
     * @return 键值对
     */
    @RequestMapping("/classCount")
    @ResponseBody
    public ArrayList<MonitorNode> getClassCount(){
       ArrayList<MonitorNode> list = new ArrayList<MonitorNode>();
       list.add(new MonitorNode(getTaskSuccessSum(),"成功任务"));
       list.add(new MonitorNode(getTaskFailSum(),"失败任务"));
       list.add(new MonitorNode(getTaskWaitSum(),"待接任务"));
       list.add(new MonitorNode(getTaskingSum(),"执行任务"));
       return list;
    }
}
