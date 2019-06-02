package xsl.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xsl.erp.commons.XslResult;
import xsl.erp.pojo.XslPush;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslPushService;

import java.util.List;

@Controller
@RequestMapping("/push")
public class XslPushController   {
    @Autowired
    private XslPushService xslPushService;


    @RequestMapping("/list")
    @ResponseBody
    PageObject selectAllPush(Integer pageIndex,Integer pageSize){
        return xslPushService.selectAllPush(pageIndex+1,pageSize);
    }
    @RequestMapping("/add")
    @ResponseBody
    XslResult addPush(@RequestBody List<XslPush> lists){
       return result(xslPushService.addPush(lists));
    }
    @RequestMapping("/update")
    @ResponseBody
    XslResult modifyPush(@RequestBody List<XslPush> lists){
       return result( xslPushService.modifyPush(lists));
    }
    @RequestMapping("/delete")
    @ResponseBody
    XslResult deletePush(@RequestBody List<XslPush> lists){
       return result( xslPushService.deletePush(lists));
    }
    @RequestMapping("/send")
    @ResponseBody
    boolean sendPush(@RequestBody XslPush xslPush){
       return  xslPushService.sendPush(xslPush);
    }

   XslResult result (boolean bool){
       if(bool) {
           return XslResult.build(200,  "成功!");
       } else {
           return XslResult.build(500,"服务器错误");
       }

   }

}
