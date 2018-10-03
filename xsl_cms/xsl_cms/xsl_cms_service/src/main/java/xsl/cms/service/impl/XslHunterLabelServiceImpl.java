package xsl.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.cms.mapper.XslHunterTagMapper;
import com.xsl.cms.mapper.XslTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xsl.cms.Utils.DateUtils;
import xsl.cms.annotation.SystemServiceLog;
import xsl.cms.pojo.XslHunterTag;
import xsl.cms.pojo.XslHunterTagExample;
import xsl.cms.pojo.XslTag;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslHunterLabelService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  xsl_hunter_tag页面的服务层
 *  @author 王坤
 */
@Service
public class XslHunterLabelServiceImpl implements XslHunterLabelService {
    /* 猎人标签日志 */
    Logger logger = LoggerFactory.getLogger(XslHunterLabelServiceImpl.class);

    @Resource
    private XslHunterTagMapper xslHunterTagMapper;
    @Resource
    private XslTagMapper xslTagMapper;

    /**
     * 页面数据的查询
     * @param page 分页查询页数
     * @param rows 分页查询行数
     * @param key 任务ID
     * @return 返回一个数据的列表包含data和total
     */
    @SystemServiceLog(description = "猎人标签分页查询Service")
    @Override
    public PageObject SelectHunterLabelAll(Integer page, Integer rows,Integer key ) {
        String tag = "猎人标签分页查询";
        PageObject object = new PageObject();
        try{
            XslHunterTagExample example = new XslHunterTagExample();
            XslHunterTagExample.Criteria criteria = example.createCriteria();
            //进行判断防止程序崩溃
            if( key != null ){//标签ID
                criteria.andIdEqualTo(key);
            }
            PageHelper.startPage(page,rows);//进行分页
            List<XslHunterTag> list = this.xslHunterTagMapper.selectByExample(example);
            object.setData(list);
            PageInfo<XslHunterTag> info = new PageInfo<XslHunterTag>(list);//得到分页的信息
            //得到分页的总数量
            object.setTotal(info.getTotal());
        }catch (Exception e){
            logger.error(tag + "异常警报 :" + e.getMessage());
        }
        return object;
    }

    /**
     * 猎人标签管理的插入
     * ① 标签添加
     * ② 标签表的数量进行增加
     * @author 王坤
     * @time 2018-8-12 22:28
     * @param xslHunterTags
     * @return
     */
    @SystemServiceLog(description = "猎人标签添加Service")
    @Override
    public boolean InsertXslHunterLabel(XslHunterTag[] xslHunterTags) {
        String tag = "猎人标签添加";
        //进行添加，查看是否是null
        if( xslHunterTags != null ){ //判断不等于null
            for(XslHunterTag xslHunterTag:xslHunterTags){
                try{
                    if( xslHunterTag != null ){ //判断不等于null
                        //1.标签记录的插入
                        //设置创建时间
                        xslHunterTag.setCreatedate(DateUtils.getDateToString());
                        int n = this.xslHunterTagMapper.insertSelective(xslHunterTag);
                        if( n < 0 ){
                            logger.error("xsl_hunter_Tag 插入失败!");
                            return false;
                        }
                        //2.标签表的数量进行增加
                        Integer tagId = xslHunterTag.getTagid();
                        if( tagId != null && tagId != 0 ){
                            //查看是否有这个id
                            XslTag xslTag  = xslTagMapper.selectByPrimaryKey(tagId);
                            if( xslTag != null ){
                                //数目进行++
                                xslTag.setUsenum((short)(xslTag.getUsenum() + 1));
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

    @SystemServiceLog(description = "猎人标签更新Service")
    @Override
    public boolean UpdateXslHunterLabel(XslHunterTag[] xslHunterTags) {
        String tag = "猎人标签更新";
        if(xslHunterTags != null){ //进行null的判断
            for(XslHunterTag xslHunterTag:xslHunterTags){
                try{
                    if( xslHunterTag != null ){//进行null的判断，最少要拥有id，和一个要修改的值
                        int n = this.xslHunterTagMapper.updateByPrimaryKeySelective(xslHunterTag);
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


    @SystemServiceLog(description = "猎人标签删除Service")
    @Override
    public boolean deleteXslHunterLabel(XslHunterTag[] xslHunterTags) {
        String tag = "猎人标签删除";
        if(xslHunterTags != null){ //进行null的判断
            for(XslHunterTag xslHunterTag:xslHunterTags){
                try {
                    if( xslHunterTag != null && !xslHunterTag.getState() ){//进行null的判断，最少要拥有id，和一个要修改的值
                        xslHunterTag.setState(false);
                        int n = xslHunterTagMapper.updateByPrimaryKeySelective(xslHunterTag);
                        if( n < 0 ){
                            logger.error(tag + "失败！");
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
