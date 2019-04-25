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
import xsl.erp.pojo.*;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslUserService;

import java.util.List;

/**
 *  对user_show.jsp进行操作
 *  @author 王坤
 *  */
@Controller
@RequestMapping("/user/show")
public class XslUserController {
    /* 获取XslUserController日志 */
    Logger logger = LoggerFactory.getLogger(XslUserController.class);

    @Autowired
    private XslUserService xslUserService;


    //一定注意pageindex+1这个动作，因为第一页为0
    //@SystemControllerLog(description = "用户查询")
    @RequestMapping("/list")
    @ResponseBody
    public PageObject getXslUserInfo(Integer pageIndex, Integer pageSize,String phone,Byte state){
        return xslUserService.selectUserAll(pageIndex + 1,pageSize, phone, state);
    }

    /**
     * 批量添加
     *
     * @param xslUsers
     * @return 是否成功
     */
    @RequestMapping("/add")
    @SystemControllerLog(description = "用户添加")
    @ResponseBody
    public XslResult insertXslUser(@RequestBody List<XslUser> xslUsers){
        XslResult xslResult = null;
        try {
            if(xslUsers != null){
				boolean result = xslUserService.insertXslUser(xslUsers);

				if(result){
                    return  XslResult.build(200,"用户添加成功!");
                }

                return XslResult.build(500,"用户添加失败!");
            }
        }catch (Exception e){
            logger.error("用户添加异常报警  :" + e.getMessage());
            xslResult = XslResult.build(500,"用户添加出现异常!");
        }

        return xslResult;

    }

    @RequestMapping("/update")
    @SystemControllerLog(description = "用户修改")
    @ResponseBody
    public XslResult updateXslUser(@RequestBody List<XslUser> xslUsers){
        XslResult xslResult = null;
        try {
            if(xslUsers != null){
                /* 修改成功 */
                if(this.xslUserService.updateXslUser(xslUsers)){
                    xslResult = XslResult.build(200,"用户修改成功!");
                }else{
                    xslResult = XslResult.build(200,"用户修改失败!");
                }
            }
        }catch (Exception e){
            logger.error("用户修改异常报警  :" + e.getMessage());
            xslResult = XslResult.build(400,"用户修改出现异常!");
        }finally {
            return xslResult;
        }
    }

    @RequestMapping("/delete")
    @SystemControllerLog(description = "用户删除")
    @ResponseBody
    public XslResult deleteXslUser(@RequestBody List<XslUser> xslUsers){
        XslResult xslResult = new XslResult();
        try {
            if(xslUsers != null){
                /* 删除成功 */
                if(this.xslUserService.deleteXslUser(xslUsers)){
                   xslResult = XslResult.build(200,"用户删除成功!");
                }else{
                   xslResult = XslResult.build(200,"用户删除失败!");
                }
            }
        }catch (Exception e){
            logger.error("用户删除异常报警  :" + e.getMessage());
            xslResult = XslResult.build(400,"用户删除出现异常!");
        }finally {
            return xslResult;
        }
    }



}
