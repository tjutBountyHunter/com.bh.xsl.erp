package xsl.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.erp.mapper.XslInputBillMapper;
import com.xsl.erp.mapper.XslOutputBillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xsl.erp.pojo.*;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslBillService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资金流入账单
 */
@Service
public class XslBillServiceImpl implements XslBillService {

    @Resource
    private XslInputBillMapper inputBillMapper;

    @Resource
    private XslOutputBillMapper outputBillMapper;

    private Logger logger = LoggerFactory.getLogger(XslBillServiceImpl.class);

    @Override
    public PageObject inputMoneyBill(Integer page, Integer rows) {

        XslInputBillExample example = new XslInputBillExample();

        PageObject object = new PageObject();
        try {

            PageHelper.startPage(page,rows);
            List<XslInputBill> list = inputBillMapper.selectByExample(example);

            object.setData(list);
            /* 得到分页的信息 */
            PageInfo<XslInputBill> info = new PageInfo<XslInputBill>(list);
            /* 得到分页的总数量 */
            object.setTotal(info.getTotal());
            return object;
        }catch (Exception e){
            logger.error("erp_资金流入账单分页查询失败:" + e.getMessage());
        }
        return null;
    }

    @Override
    public PageObject outputMoneyBill(Integer page, Integer rows){

        XslOutputBillExample example = new XslOutputBillExample();

        PageObject object = new PageObject();
        try {

            PageHelper.startPage(page,rows);
            List<XslOutputBill> list = outputBillMapper.selectByExample(example);

            object.setData(list);
            //得到分页的信息
            PageInfo<XslOutputBill> info = new PageInfo<XslOutputBill>(list);
            //得到分页的总数量
            object.setTotal(info.getTotal());
            return object;
        }catch (Exception e){
            logger.error("erp_资金流入账单分页查询失败:" + e.getMessage());
        }
        return null;
    }
}
