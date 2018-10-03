package xsl.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.cms.mapper.XslTagMapper;
import com.xsl.cms.mapper.XslTaskTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xsl.cms.annotation.SystemServiceLog;
import xsl.cms.pojo.XslTag;
import xsl.cms.pojo.XslTaskTag;
import xsl.cms.pojo.XslTaskTagExample;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslTaskTagService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  xslTaskTag页面的服务层
 *  @author 王坤
 */
@Service
public class XslTaskTagServiceImpl implements XslTaskTagService {
    /* 任务服务层 */
    Logger logger = LoggerFactory.getLogger(XslTaskTagServiceImpl.class);

    @Resource
    private XslTaskTagMapper xslTaskTagMapper;
    @Resource
    private XslTagMapper xslTagMapper;

    /**
     * 页面数据的查询
     * @param page 分页查询页数
     * @param rows 分页查询行数
     * @param key 任务ID
     * @param key1 任务状态
     * @return 返回一个数据的列表包含data和total
     */
    @SystemServiceLog(description = "任务标签分页查询Service")
    @Override
    public PageObject SelectTaskTagAll(Integer page, Integer rows,Integer key,Integer key1 ) {
        String tag = "任务标签查询";
        try{
            XslTaskTagExample example = new XslTaskTagExample();
            XslTaskTagExample.Criteria criteria = example.createCriteria();
            //进行判断防止程序崩溃
            if( key != null ){//任务ID
                criteria.andIdEqualTo(key);
            }
            if( key1 != null){//任务状态
                criteria.andTagidEqualTo(key1);
            }
            PageHelper.startPage(page,rows);//进行分页
            List<XslTaskTag> list = this.xslTaskTagMapper.selectByExample(example);
            if( list != null ){
                PageObject object = new PageObject();
                object.setData(list);
                PageInfo<XslTaskTag> info = new PageInfo<XslTaskTag>(list);//得到分页的信息
                //得到分页的总数量
                object.setTotal(info.getTotal());
                return object;
            }
        }catch (Exception e){
            logger.error(tag + "异常警报 :" + e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * 猎人标签管理的插入
     * ① 标签添加
     * ② 标签表的数量进行增加
     * @author 王坤
     * @time 2018-8-12 22:58
     * @param xslTaskTags
     * @return
     */
    @SystemServiceLog(description = "任务标签添加Service")
    @Override
    public boolean InsertXslTaskTag(XslTaskTag[] xslTaskTags) {
        String tag = "任务标签添加";
        //进行添加，查看是否是null
        if( xslTaskTags != null ){ //判断不等于null
            for(XslTaskTag xslTaskTag:xslTaskTags){
                try{
                    if( xslTaskTag != null ) {
                        //1.标签的添加
                        xslTaskTag.setCreatedate(new Date());
                        int n = this.xslTaskTagMapper.insertSelective(xslTaskTag);
                        if (n < 0) {
                            logger.info(tag + "失败!");
                            return false;
                        }
                        //2.标签表的数量进行增加
                        Integer tagId = xslTaskTag.getTagid();
                        if (tagId != null && tagId != 0) {
                            //查看是否有这个id
                            XslTag xslTag = xslTagMapper.selectByPrimaryKey(tagId);
                            if (xslTag != null) {
                                //数目进行++
                                xslTag.setUsenum((short) (xslTag.getUsenum() + 1));
                                xslTagMapper.updateByPrimaryKeySelective(xslTag);
                            }
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

    @SystemServiceLog(description = "任务标签更新Service")
    @Override
    public boolean UpdateXslTaskTag(XslTaskTag[] xslTaskTags) {
        String tag = "任务标签更新";
        if(xslTaskTags != null){ //进行null的判断
            for(XslTaskTag xslTaskTag:xslTaskTags){
                try{
                    if( xslTaskTag != null ){//进行null的判断，最少要拥有id，和一个要修改的值
                        System.out.println(xslTaskTag.getTaskid());
                        int n = this.xslTaskTagMapper.updateByPrimaryKeySelective(xslTaskTag);
                        if(n < 0){
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

    @SystemServiceLog(description = "任务标签删除Service")
    @Override
    public boolean deleteXslTaskTag(XslTaskTag[] xslTaskTags) {
        String tag = "任务标签删除";
        if(xslTaskTags != null){ //进行null的判断
            for(XslTaskTag xslTaskTag:xslTaskTags){
                try{
                    if( xslTaskTag != null && !xslTaskTag.getState() ){//进行null的判断，最少要拥有id，和一个要修改的值
                        xslTaskTag.setState(false);
                        int n = xslTaskTagMapper.updateByPrimaryKeySelective(xslTaskTag);
                        if( n < 0 ){
                            System.out.println("xsl_task_tag 删除失败!");
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
