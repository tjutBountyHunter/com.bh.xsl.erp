package xsl.erp.service;

import xsl.erp.pojo.XslPush;
import xsl.erp.pojo.common.PageObject;

import java.util.List;

public interface XslPushService {
    PageObject selectAllPush(Integer pageIndex,Integer pageSize);
    boolean addPush(List<XslPush> lists);
    boolean modifyPush(List<XslPush> lists);
    boolean deletePush(List<XslPush> lists);
    boolean sendPush(XslPush xslPush);
}
