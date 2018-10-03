package xsl.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.cms.mapper.XslMasterTagMapper;
import com.xsl.cms.mapper.XslTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xsl.cms.annotation.SystemServiceLog;
import xsl.cms.pojo.XslMasterTag;
import xsl.cms.pojo.XslMasterTagExample;
import xsl.cms.pojo.XslTag;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslMasterLabelService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  xsl_master_tag页面的服务层
 *  @author 王坤
 */
@Service
public class XslMasterLabelServiceImpl implements XslMasterLabelService {
    /* 雇主标签服务 */
    Logger logger = LoggerFactory.getLogger(XslMasterLabelServiceImpl.class);

    @Resource
    private XslMasterTagMapper xslMasterTagMapper;
    @Resource
    private XslTagMapper xslTagMapper;

    /**
     * 页面数据的查询
     * @param page 分页查询页数
     * @param rows 分页查询行数
     * @param key 任务ID
     * @return 返回一个数据的列表包含data和total
     */
    @SystemServiceLog(description = "雇主标签分页查询Service")
    @Override
    public PageObject SelectMasterLabelAll(Integer page, Integer rows,Integer key ) {
        String tag = "雇主标签分页查询";
        PageObject object = new PageObject();
        try{
            XslMasterTagExample example = new XslMasterTagExample();
            XslMasterTagExample.Criteria criteria = example.createCriteria();
            //进行判断防止程序崩溃
            if( key != null ){//标签ID
                criteria.andIdEqualTo(key);
            }
            PageHelper.startPage(page,rows);//进行分页
            List<XslMasterTag> list = this.xslMasterTagMapper.selectByExample(example);
            object.setData(list);
            PageInfo<XslMasterTag> info = new PageInfo<XslMasterTag>(list);//得到分页的信息
            //得到分页的总数量
            object.setTotal(info.getTotal());
        }catch (Exception e){
            logger.error(tag + "异常警报 :" + e.getMessage());
        }finally {
            return object;
        }

    }

    /**
     * 猎人标签管理的插入
     * ① 标签添加
     * ② 标签表的数量进行增加
     * @author 王坤
     * @time 2018-8-12 22:58
     * @param xslMasterTags
     * @return 添加是否成功
     */
    @SystemServiceLog(description = "雇主标签添加Service")
    @Override
    public boolean InsertXslMasterLabel(XslMasterTag[] xslMasterTags) {
        String tag = "雇主标签添加";
        //进行添加，查看是否是null
        if( xslMasterTags != null ){ //判断不等于null
            for(XslMasterTag xslMasterTag:xslMasterTags){
                try {
                    if( xslMasterTag != null ){ //判断不等于null
                        //1.标签添加
                        //设置创建时间
                        xslMasterTag.setCreatedate(new Date());
                        int n = this.xslMasterTagMapper.insertSelective(xslMasterTag);
                        if( n < 0 ){
                            logger.error("xsl_master_tag 添加失败!");
                            return false;
                        }
                        //2.标签表的数量进行增加
                        Integer tagId = xslMasterTag.getTagid();
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

    @SystemServiceLog(description = "雇主标签更新Service")
    @Override
    public boolean UpdateXslMasterLabel(XslMasterTag[] xslMasterTags) {
        String tag = "雇主标签更新";
        if(xslMasterTags != null){ //进行null的判断
            for(XslMasterTag xslMasterTag:xslMasterTags){
                try{
                    if( xslMasterTag != null ){//进行null的判断，最少要拥有id，和一个要修改的值
                        int n = this.xslMasterTagMapper.updateByPrimaryKeySelective(xslMasterTag);
                        if(n < 0){
                            logger.error(tag + "失败!");
                            return false;
                        }
                    }
                }catch (Exception e){
                    logger.error(tag + "异常警报 :" + e.getMessage());
                    return  false;
                }
            }
        }
        return true;
    }


    @SystemServiceLog(description = "雇主标签删除Service")
    @Override
    public boolean deleteXslMasterLabel(XslMasterTag[] xslMasterTags) {
        String tag = "雇主标签删除";
        if(xslMasterTags != null){ //进行null的判断
            for(XslMasterTag xslMasterTag:xslMasterTags){
                try{
                    if( xslMasterTag != null && !xslMasterTag.getState() ){//进行null的判断，最少要拥有id，和一个要修改的值
                        xslMasterTag.setState(false);
                        int n = xslMasterTagMapper.updateByPrimaryKeySelective(xslMasterTag);
                        if( n < 0){
                            logger.error("xsl_master_tag 删除失败!");
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
