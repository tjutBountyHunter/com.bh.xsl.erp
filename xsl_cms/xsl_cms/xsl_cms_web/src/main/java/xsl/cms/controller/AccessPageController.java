package xsl.cms.controller;
/**
 * 页面访问控制
 */
import Utils.JedisClient;
import Utils.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import xsl.cms.pojo.XslManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AccessPageController {
    @Resource
    private JedisClient jedisClient;

    /**
     * 主页面访问
     *
     * @return index.jsp
     */
    @RequestMapping("/")
    public String accessIndex(HttpServletRequest request) {
        XslManager managerInfo = JwtUtils.getManagerInfo(request, jedisClient);
        request.setAttribute("managerName" ,managerInfo.getManagerName());
        return "index";
    }

    /**
     * user_show访问（用户操作-用户展示）
     *
     * @return user_show.jsp
     */
    @RequestMapping("/user/show")
    public String accessUserShow() {
        return "user_show";
    }

    /**
     * user_approve页面访问（用户操作-用户审计）
     *
     * @return user_approve.jsp
     */
    @RequestMapping("/user/approve")
    public String accessUserApprove() {
        return "user_approve";
    }

    /**
     * user_hunter访问（用户操作-猎人）
     *
     * @return user_hunter.jsp
     */
    @RequestMapping("/user/hunter")
    public String accessUserHunter() {
        return "user_hunter";
    }

    /**
     * user_empolyer访问（用户操作-雇主）
     *
     * @return user_empolyer.jsp
     */
    @RequestMapping("/user/empolyer")
    public String accessUserEmployer() {
        return "user_empolyer";
    }
    //*********************************************下面是任务的页面访问

    /**
     * task_show访问（任务操作-任务展示）
     *
     * @return task_show.jsp
     */
    @RequestMapping("/task/show")
    public String accessTaskShow() {
        return "task_show";
    }

    /**
     * task_approve访问（任务操作-任务审计）
     *
     * @return task_approve.jsp
     */
    @RequestMapping("/task/approve")
    public String accessTaskApprove() {
        return "task_approve";
    }

    /**
     * task_class访问（任务操作-任务分类）
     *
     * @return task_class.jsp
     */
    @RequestMapping("/task/class")
    public String accessTaskClass() {
        return "task_class";
    }

    /**
     * task_label访问（任务操作-任务标签）
     *
     * @return task_label.jsp
     */
    @RequestMapping("/task/label")
    public String accessTaskLabel() {
        return "task_label";
    }

    //************************************客户端操作

    /**
     * client_message访问（客户端操作-客户端消息）
     *
     * @return client_message.jsp
     */
    @RequestMapping("/client/message")
    public String accessClientMessage() {
        return "client_message";
    }

    /**
     * client_control访问（客户端操作-客户端消息）
     *
     * @return client_control.jsp
     */
    @RequestMapping("/client/control")
    public String accessClientControl() {
        return "client_control";
    }

    //**********************************************下面是日志页面

    /**
     * log_SQL访问（SQL日志）
     *
     * @return log_SQL.jsp
     */
    @RequestMapping("/log/SQL")
    public String accesslogSQL() {
        return "log_SQL";
    }

    /**
     * log_program访问（程序运行日志）
     *
     * @return log_program.jsp
     */
    @RequestMapping("/log/program")
    public String accesslogControl() {
        return "log_program";
    }
    //*********************************************下面是监控的网页访问

    /**
     * monitor_user_count访问
     *
     * @return monitor_user_count.jsp
     */
    @RequestMapping("/monitor/user/count")
    public String accessMonitorUserCount() {
        return "monitor_user_count";
    }

    /**
     * monitor_user_activity访问
     *
     * @return monitor_user_activity.jsp
     */
    @RequestMapping("/monitor/user/activity")
    public String accessMonitorUserActivity() {
        return "monitor_user_activity";
    }

    /**
     * monitor_task_count访问
     *
     * @return monitor_task_count.jsp
     */
    @RequestMapping("/monitor/task/count")
    public String accessMonitorTaskCount() {
        return "monitor_task_count";
    }

    /**
     * monitor_task_label访问
     *
     * @return monitor_task_label.jsp
     */
    @RequestMapping("/monitor/task/label")
    public String accessMonitorTaskLabel() {
        return "monitor_task_label";
    }

    /**
     * monitor_task_label访问
     *
     * @return monitor_task_label.jsp
     */
    @RequestMapping("/monitor/task/class")
    public String accessMonitorTaskClass() {
        return "monitor_task_class";
    }

    /**
     * monitor_task_success访问
     *
     * @return monitor_task_success.jsp
     */
    @RequestMapping("/monitor/task/success")
    public String accessMonitorTaskSuccess() {
        return "monitor_task_success";
    }

    //**********************性能监控
    /**
     * monitor_performance_DB访问
     *
     * @return monitor_performance_DB.jsp
     */
    @RequestMapping("/monitor/performance/DB")
    public String accessMonitorPerformanceDB() {
        return "monitor_performance_DB";
    }

    /**
     * monitor_performance_server访问
     * @return monitor_performance_server.jsp
     */
    @RequestMapping("/monitor/performance/server")
    public String accessMonitorPerformanceServer() {
        return "monitor_performance_server";
    }

    /**
     * monitor_performance_redis访问
     * @return monitor_performance_redis.jsp
     */
    @RequestMapping("/monitor/performance/redis")
    public String accessMonitorPerformanceRedis() {
        return "monitor_performance_redis";
    }

    /**
     * monitor_performance_memory访问
     * @return monitor_performance_memory.jsp
     */
    @RequestMapping("/monitor/performance/memory")
    public String accessMonitorPerformanceMemory() {
        return "monitor_performance_memory";
    }

    //**************************模态窗口
    @RequestMapping("/update/password")
    public String accessUpdatePassword() {
        return "updatePassword";
    }

    //*************************登录
    @RequestMapping("/login")
    public String accessLogin() {
        return "Login";
    }
    //*************************标签
    @RequestMapping("/client/label")
    public String accessClientLabel() {
        return "client_label";
    }
    //*************************雇主标签
    @RequestMapping("/master/label")
    public String accessUserMasterLabel() {
        return "user_master_label";
    }
    //*************************猎人标签
    @RequestMapping("/hunter/label")
    public String accessUserHunterLabel() {
        return "user_hunter_label";
    }
    //************************Controller日志页面
    @RequestMapping("/log/controller")
    public String accessControllerLogLabel() {
        return "log_controller";
    }
    //************************Controller日志页面
    @RequestMapping("/log/service")
    public String accessThrowServiceLogLabel() {
        return "log_throwservice";
    }
}