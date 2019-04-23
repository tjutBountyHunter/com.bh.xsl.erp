package xsl.erp.service;

import xsl.erp.pojo.XslUser;
import xsl.erp.pojo.common.PageObject;

//xml用户的service操作定义
public interface XslUserService {
    PageObject SelectUserAll(Integer page, Integer rows,Integer key,Byte key1);
    boolean InsertXslUser(XslUser[] xslUsers);
    boolean UpdateXslUser(XslUser[] xslUsers);
    boolean deleteXslUser(XslUser[] xslUsers);
    //********user_approve的两个操作
    PageObject SelectUserApprove(Integer page, Integer rows);
    boolean approve(XslUser xslUser);
    //********一些用户的监控
    Integer countUser();
}