package xsl.erp.service;

import xsl.erp.pojo.XslUser;
import xsl.erp.pojo.common.PageObject;

import java.util.List;

//xml用户的service操作定义
public interface XslUserService {

    PageObject selectUserAll(Integer page, Integer rows, String phone, Byte state);

    boolean insertXslUser(List<XslUser> xslUsers);

    boolean updateXslUser(List<XslUser> xslUsers);

    boolean deleteXslUser(List<XslUser> xslUsers);

    //********user_approve的两个操作
    PageObject SelectUserApprove(Integer page, Integer rows);

    boolean approve(XslUser xslUser);

    //********一些用户的监控
    Integer countUser();
}