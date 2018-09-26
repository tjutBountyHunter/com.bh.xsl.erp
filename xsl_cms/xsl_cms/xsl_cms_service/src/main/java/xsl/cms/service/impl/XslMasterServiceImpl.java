package xsl.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xsl.cms.annotation.SystemServiceLog;
import xsl.cms.mapper.XslMasterMapper;
import xsl.cms.pojo.XslMaster;
import xsl.cms.pojo.XslMasterExample;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslMasterService;

import javax.annotation.Resource;
import java.util.List;

/**
 *  xsl_maxter 页面的服务层
 *  @author 王坤
 */
@Service
public class XslMasterServiceImpl implements XslMasterService {
    /* 雇主日志 */
    Logger logger = LoggerFactory.getLogger(XslMasterServiceImpl.class);

    @Resource
    protected XslMasterMapper xslMasterMapper;

    /**
     * 页面数据的查询
     * @param page 分页查询页数
     * @param rows 分页查询行数
     * @param key 用户ID
     * @param key1 用户状态
     * @return 返回一个数据的列表包含data和total
     */
    @SystemServiceLog(description = "雇主分页查询Service")
    @Override
    public PageObject SelectMasterAll(Integer page, Integer rows,Integer key,Short key1 ) {
        String tag = "雇主分页查询";
        PageObject object = new PageObject();
        try{
            XslMasterExample example = new XslMasterExample();
            XslMasterExample.Criteria criteria = example.createCriteria();
            //进行判断防止程序崩溃
            if( key != null ){//用户ID
                criteria.andIdEqualTo(new Integer(key));
            }
            if( key1 != null){//用户等级
                criteria.andLevelEqualTo(key1);
            }
            //只显示没有被删除的数据
            criteria.andStateEqualTo(true);
            PageHelper.startPage(page,rows);//进行分页
            List<XslMaster> list = this.xslMasterMapper.selectByExample(example);
            object.setData(list);
            PageInfo<XslMaster> info = new PageInfo<XslMaster>(list);//得到分页的信息
            //得到分页的总数量
            object.setTotal(info.getTotal());
        }catch (Exception e){
            logger.error(tag + "异常警报 :" + e.getMessage());
        }finally {
            return object;
        }
    }

    /**
     * 更新页面数据，只更新修改过的数据（批量操作）
     * @param xslMasters 携带更新数据的pojo
     * @return 是否更新成功
     */
    @SystemServiceLog(description = "雇主更新Service")
    @Override
    public boolean UpdateXslMaster(XslMaster[] xslMasters) {
        String  tag = "雇主更新";
        if(xslMasters != null){ //进行null的判断
            for(XslMaster xslMaster:xslMasters){
                try {
                    if( xslMaster != null ){//进行null的判断，最少要拥有id，和一个要修改的值
                        int n = this.xslMasterMapper.updateByPrimaryKeySelective(xslMaster);
                        if(n < 0){
                            logger.error(tag + "失败!");
                            return false;
                        }
                    }
                }catch (Exception e){
                    logger.error(tag + "异常警报 :" + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }
}
