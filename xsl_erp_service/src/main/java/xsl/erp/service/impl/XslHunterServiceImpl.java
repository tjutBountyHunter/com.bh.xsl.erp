package xsl.erp.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.erp.mapper.XslExtendMapper;
import com.xsl.erp.mapper.XslHunterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xsl.erp.annotation.SystemServiceLog;
import xsl.erp.commons.XslResult;
import xsl.erp.pojo.HunterReturn;
import xsl.erp.pojo.XslHunter;
import xsl.erp.pojo.XslHunterExample;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslHunterService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *  xsl_hunter 页面的服务层
 *  @author 王坤
 */
@Service
public class XslHunterServiceImpl implements XslHunterService {
    /* 猎人服务 */
    Logger logger = LoggerFactory.getLogger(XslHunterServiceImpl.class);

    @Resource
    protected XslHunterMapper xslHunterMapper;
    @Resource
    private XslExtendMapper xslExtendMapper;

    /**
     * 页面数据的查询
     * @param page 分页查询页数
     * @param rows 分页查询行数
     * @param level 用户等级
     * @return 返回一个数据的列表包含data和total
     */
    @SystemServiceLog(description = "猎人分页查询Service")
    @Override
    public PageObject selectHunterAll(Integer page, Integer rows,Short level) {
        PageObject object = new PageObject();

        try{
            XslHunterExample example = new XslHunterExample();
            XslHunterExample.Criteria criteria = example.createCriteria();
            criteria.andStateEqualTo(true);

            if(level != null){//用户等级
                criteria.andLevelEqualTo(level);
            }

            PageHelper.startPage(page,rows);//进行分页
			List<XslHunter> xslHunters = xslHunterMapper.selectByExample(example);

			List<HunterReturn> hunterReturns = setUserNameInHunterReturn(xslHunters);

			object.setData(hunterReturns);
            PageInfo<XslHunter> info = new PageInfo<>(xslHunters);//得到分页的信息
            //得到分页的总数量
            object.setTotal(info.getTotal());
            return object;
        }catch (Exception e){
            logger.error("猎人分页查询异常警报 : {}", e);
            throw new RuntimeException();
        }
    }

    /**
     * 更新页面数据，只更新修改过的数据（批量操作）
     * @param xslHunters 携带更新数据的pojo
     * @return 是否更新成功
     */
    @SystemServiceLog(description = "猎人更新Service")
    @Override
    public XslResult updateXslHunter(List<XslHunter> xslHunters) {
        if(xslHunters == null || xslHunters.size() < 1){
        	return XslResult.build(400, "参数错误");
		}

		for(XslHunter xslHunter:xslHunters){
			try{
				int n = xslHunterMapper.updateByPrimaryKeySelective(xslHunter);
				if(n > 0){
					return XslResult.build(200, "更新成功");
				}
			}catch (Exception e){
				logger.error("猎人更新异常警报 : {}",e);
				throw new RuntimeException();
			}

		}

		return XslResult.build(500, "更新失败");
    }

    private List<HunterReturn> setUserNameInHunterReturn(List<XslHunter> xslHunters){
		List<HunterReturn> hunterReturns = new ArrayList<>();
    	for (XslHunter xslHunter : xslHunters){
			HunterReturn hunterReturn = new HunterReturn();
    		String name = xslExtendMapper.getUserNameByHunterId(xslHunter.getHunterid());
			BeanUtils.copyProperties(xslHunter, hunterReturn);
			hunterReturn.setUserName(name);
			hunterReturns.add(hunterReturn);
        }

        return hunterReturns;
    }
}
