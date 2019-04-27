package xsl.erp.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.erp.mapper.XslExtendMapper;
import com.xsl.erp.mapper.XslMasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xsl.erp.annotation.SystemServiceLog;
import xsl.erp.commons.XslResult;
import xsl.erp.pojo.*;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslMasterService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *  xsl_master 页面的服务层
 */
@Service
public class XslMasterServiceImpl implements XslMasterService {
    /* 雇主日志 */
    Logger logger = LoggerFactory.getLogger(XslMasterServiceImpl.class);

    @Resource
    protected XslMasterMapper xslMasterMapper;
    @Resource
    private XslExtendMapper xslExtendMapper;

    /**
     * 页面数据的查询
     * @param page 分页查询页数
     * @param rows 分页查询行数
     * @param level 雇主等级
     * @return 返回一个数据的列表包含data和total
     */
    @SystemServiceLog(description = "雇主分页查询Service")
    @Override
    public PageObject selectMasterAll(Integer page, Integer rows, Short level) {
        PageObject object = new PageObject();
        try{
            XslMasterExample example = new XslMasterExample();
            XslMasterExample.Criteria criteria = example.createCriteria();
            criteria.andStateEqualTo(true);

            if(level != null){//用户等级
                criteria.andLevelEqualTo(level);
            }

            PageHelper.startPage(page,rows);//进行分页
            List<XslMaster> xslMasters = xslMasterMapper.selectByExample(example);

            List<MasterReturn> masterReturns = setUserNameInMasterReturn(xslMasters);

            object.setData(masterReturns);
            PageInfo<XslMaster> info = new PageInfo<>(xslMasters);//得到分页的信息
            //得到分页的总数量
            object.setTotal(info.getTotal());
            return object;
        }catch (Exception e){
            logger.error("雇主分页查询异常警报 : {}", e);
            throw new RuntimeException();
        }
    }

    /**
     * 更新页面数据，只更新修改过的数据（批量操作）
     * @param xslMasters 携带更新数据的pojo
     * @return 是否更新成功
     */
    @SystemServiceLog(description = "雇主更新Service")
    @Override
    public XslResult updateXslMaster(List<XslMaster> xslMasters) {
        if(xslMasters == null || xslMasters.size() < 1){
            return XslResult.build(400, "参数错误");
        }

        for(XslMaster xslMaster : xslMasters){
            try{
                int n = xslMasterMapper.updateByPrimaryKeySelective(xslMaster);
                if(n > 0){
                    return XslResult.build(200, "更新成功");
                }
            }catch (Exception e){
                logger.error("雇主更新异常警报 : {}",e);
                throw new RuntimeException();
            }

        }

        return XslResult.build(500, "更新失败");
    }

    private List<MasterReturn> setUserNameInMasterReturn(List<XslMaster> xslMasters){
        List<MasterReturn> masterReturns = new ArrayList<>();
        for (XslMaster xslMaster : xslMasters){
            MasterReturn masterReturn = new MasterReturn();
            String name = xslExtendMapper.getUserNameByMasterId(xslMaster.getMasterid());
            BeanUtils.copyProperties(xslMaster, masterReturn);
            masterReturn.setUserName(name);
            masterReturns.add(masterReturn);
        }

        return masterReturns;
    }
}
