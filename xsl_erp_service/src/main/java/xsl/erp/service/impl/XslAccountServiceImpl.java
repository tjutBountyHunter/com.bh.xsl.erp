package xsl.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.erp.mapper.XslAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xsl.erp.pojo.XslAccount;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslAccountService;

import java.util.List;

@Service
public class XslAccountServiceImpl implements XslAccountService {
    @Autowired
    private XslAccountMapper xslAccountMapper;

    @Override
    public PageObject selectAll(Integer page, Integer rows) {
        PageObject result = new PageObject();
        try {
            PageHelper.startPage(page,rows);
            List<XslAccount> accountList=xslAccountMapper.selectAll();
            result.setData(accountList);
            PageInfo<XslAccount> info = new PageInfo<XslAccount>(accountList);
            result.setTotal(info.getTotal());
            return result;
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }
}
