package xsl.cms.service;

import xsl.cms.pojo.common.PageObject;

public interface XslBillService {

    PageObject inputMoneyBill(Integer page, Integer rows);

    PageObject outputMoneyBill(Integer page, Integer rows);

}
