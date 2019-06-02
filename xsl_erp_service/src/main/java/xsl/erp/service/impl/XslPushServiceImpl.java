package xsl.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.erp.mapper.XslPushMapper;
import com.xsl.user.JpushResource;
import com.xsl.user.vo.JPushReqVo;
import com.xsl.user.vo.ResBaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xsl.erp.commons.UUIDUtil;
import xsl.erp.pojo.XslPush;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslPushService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class XslPushServiceImpl implements XslPushService {
@Autowired
private XslPushMapper xslPushMapper;
@Resource
private JpushResource jpushResource;
    @Override
    public PageObject selectAllPush(Integer pageIndex,Integer pageSize) {
        PageObject pageObject=new PageObject();
        PageHelper.startPage(pageIndex,pageSize);
        try {
            PageHelper.startPage(pageIndex,pageSize);
            List<XslPush> pushList=xslPushMapper.selectAll();
            pageObject.setData(pushList);
            PageInfo<XslPush> pageInfo=new PageInfo<>(pushList);
            pageObject.setTotal(pageInfo.getTotal());
            return pageObject;

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addPush(List<XslPush> lists) {
        if(lists ==null || lists.size()==0){
            return false;
        }
        try {
            lists.forEach(xslPush->{
                if(xslPush.getPushId()==""||xslPush.getPushId()==null) {
                    xslPush.setPushId(UUIDUtil.getUUID().substring(0,6));
                }
                xslPushMapper.addPush(xslPush);
            });
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean modifyPush(List<XslPush> lists) {
        if(lists ==null || lists.size()==0){
            return false;
        }
        try {
            lists.forEach(xslPush->xslPushMapper.alterPush(xslPush));
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deletePush(List<XslPush> lists) {
        if(lists ==null || lists.size()==0){
            return false;
        }
        try {
            lists.forEach(xslPush->xslPushMapper.deletePush(xslPush));
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean sendPush(XslPush xslPush) {
        if(xslPush ==null){
           return false;
        }
        try {
            if(jpushResource.sendAll(initVo(xslPush)).isOK()) {
                return true;
            }
            else {
                return false;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
   private JPushReqVo initVo(XslPush xslPush){
        JPushReqVo jPushReqVo=new JPushReqVo();
        jPushReqVo.setMsgContent(xslPush.getMsg_content());
        jPushReqVo.setExtrasparam("");
        jPushReqVo.setMsgTitle(xslPush.getMsg_title());
        jPushReqVo.setNotificationTitle(xslPush.getNotification_content());
        jPushReqVo.setSource("XSL");
        return jPushReqVo;

   }



}
