package xsl.erp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslBillService;

import javax.annotation.Resource;

/**
 * 流水账单Controller层
 */
@Controller
@RequestMapping("/bill")
public class XslBillController {

    @Resource
    private XslBillService billService;

    @RequestMapping("/inputBill")
    public String showInputBill(){
        return "bill_input";
    }

    @RequestMapping("/inputBill/list")
    @ResponseBody
    public PageObject getInputBillMoney(Integer page,Integer rows){
        PageObject object = billService.inputMoneyBill(page,rows);
        return object;
    }

    @RequestMapping("/outputBill")
    public String showOutputBill(){
        return "bill_output";
    }

    @RequestMapping("/outputBill/list")
    @ResponseBody
    public PageObject getOutputBillMoney(Integer page,Integer rows){
        PageObject object = billService.outputMoneyBill(page,rows);
        return object;
    }
}
