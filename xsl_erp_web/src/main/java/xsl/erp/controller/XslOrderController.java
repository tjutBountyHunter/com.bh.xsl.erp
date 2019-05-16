package xsl.erp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import resource.OrderInfoResource;
import vo.OrderReqVo;
import vo.PageObject;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/order/show")
public class XslOrderController {
    @Resource
    private OrderInfoResource orderInfoResource;
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getOrderInfo(Integer pageIndex,Integer pageSize){
     OrderReqVo orderReqVo=new OrderReqVo();
     orderReqVo.setRows(pageSize);
     orderReqVo.setPage(pageIndex+1);
      return orderInfoResource.getList(orderReqVo);


    }
}
