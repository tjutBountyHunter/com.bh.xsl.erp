package xsl.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.cms.mapper.XslExtendMapper;
import com.xsl.cms.mapper.XslHunterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xsl.cms.annotation.SystemServiceLog;
import xsl.cms.pojo.HunterReturn;
import xsl.cms.pojo.XslHunter;
import xsl.cms.pojo.XslHunterExample;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslHunterService;

import javax.annotation.Resource;
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
     * @param key 用户ID
     * @param key1 用户状态
     * @return 返回一个数据的列表包含data和total
     */
    @SystemServiceLog(description = "猎人分页查询Service")
    @Override
    public PageObject SelectHunterAll(Integer page, Integer rows,Integer key,Short key1 ) {
        String tag = "猎人分页查询";
        PageObject object = new PageObject();
        try{
            XslHunterExample example = new XslHunterExample();
            XslHunterExample.Criteria criteria = example.createCriteria();
            criteria.andStateEqualTo(true);
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
            List<HunterReturn> list = this.xslHunterMapper.selectByExample(example);
            setUserNameInHunterReturn(list);
            object.setData(list);
            PageInfo<HunterReturn> info = new PageInfo<HunterReturn>(list);//得到分页的信息
            //得到分页的总数量
            object.setTotal(info.getTotal());
        }catch (Exception e){
            logger.error(tag + "异常警报 :"  + e.getMessage());
        }
        return object;
    }

    /**
     * 更新页面数据，只更新修改过的数据（批量操作）
     * @param xslHunters 携带更新数据的pojo
     * @return 是否更新成功
     */
    @SystemServiceLog(description = "猎人更新Service")
    @Override
    public boolean UpdateXslHunter(XslHunter[] xslHunters) {
        String tag = "猎人更新";
        if(xslHunters != null){ //进行null的判断
            for(XslHunter xslHunter:xslHunters){
                if( xslHunter != null ){//进行null的判断，最少要拥有id，和一个要修改的值
                    try{
                        int n = this.xslHunterMapper.updateByPrimaryKeySelective(xslHunter);
                        if(n < 0){
                            logger.error(tag + "失败!");
                            return false;
                        }
                    }catch (Exception e){
                        logger.error(tag + "异常警报 :" + e.getMessage());
                        return  false;
                    }
                }
            }
        }
        return true;
    }

    private void setUserNameInHunterReturn(List<HunterReturn> list){
        for (HunterReturn hunterReturn : list){
            String name = xslExtendMapper.getUserNameByHunterId(hunterReturn.getId());
            hunterReturn.setUserName(name);
        }
    }
}
