package xsl.erp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.erp.commons.JedisClient;

import javax.annotation.Resource;

/**
 * 资金流水账单Controller层
 */
@Controller
@RequestMapping("/monitor")
public class MonitorBillController {

    @Resource
    private JedisClient jedisClient;

    @Value("MONEY_BASE_KEY")
    private String MONEY_BASE_KEY;

    private Logger logger = LoggerFactory.getLogger(MonitorBillController.class);

    @RequestMapping("/bill")
    public String showMonitorMontey(){
        return "monitor_bill_money";
    }


    @RequestMapping("/billMoney")
    @ResponseBody
    public Double monitorBill(){
        String stringMoney = jedisClient.get(MONEY_BASE_KEY);
        Double money = new Double(stringMoney);
        logger.info("monet is {}"+money);
        return money;
    }
}
