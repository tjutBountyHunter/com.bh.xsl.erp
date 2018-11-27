package xsl.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.cms.mapper.XslHunterTagMapper;
import com.xsl.cms.mapper.XslMasterTagMapper;
import com.xsl.cms.mapper.XslTagMapper;
import com.xsl.cms.mapper.XslTaskTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xsl.cms.Utils.DateUtils;
import xsl.cms.annotation.SystemServiceLog;
import xsl.cms.pojo.*;
import xsl.cms.pojo.common.MonitorNode;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslClientLabelService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *  xsl_user页面的服务层
 *  @author 王坤
 */
@Service
public class XslClientLabelServiceImpl implements XslClientLabelService {
    /* 客户端标签日志 */
    Logger logger = LoggerFactory.getLogger(XslClientLabelServiceImpl.class);

    @Resource
    private XslTagMapper xslTagMapper;
    @Resource
    private XslHunterTagMapper xslHunterTagMapper;
    @Resource
    private XslMasterTagMapper xslMasterTagMapper;
    @Resource
    private XslTaskTagMapper xslTaskTagMapper;

    /**
     * 页面数据的查询
     * @param page 分页查询页数
     * @param rows 分页查询行数
     * @param key 任务ID
     * @return 返回一个数据的列表包含data和total
     */
    @SystemServiceLog(description = "标签分页查询Service")
    @Override
    public PageObject SelectClientLabelAll(Integer page, Integer rows,Integer key ) {
        String tag = "标签分页查询";
        PageObject object = new PageObject();
        try{
            XslTagExample example = new XslTagExample();
            XslTagExample.Criteria criteria = example.createCriteria();
            //进行判断防止程序崩溃
            if( key != null ){//标签ID
                criteria.andIdEqualTo(key);
            }
            PageHelper.startPage(page,rows);//进行分页
            List<XslTag> list = this.xslTagMapper.selectByExample(example);
            object.setData(list);
            PageInfo<XslTag> info = new PageInfo<XslTag>(list);//得到分页的信息
            //得到分页的总数量
            object.setTotal(info.getTotal());
        }catch (Exception e){
            logger.error(tag + "异常警报 :" + e.getMessage());
        }finally {
            return object;
        }
    }

    @SystemServiceLog(description = "标签添加Service")
    @Override
    public boolean InsertXslClientLabel(XslTag[] xslTags) {
        String tag = "标签添加";
        //进行添加，查看是否是null
        if( xslTags != null ){ //判断不等于null
            for(XslTag xslTag:xslTags){
                if( xslTag != null ){ //判断不等于null
                    try{

                        //设置创建时间
                        xslTag.setCreatedate(DateUtils.getDateToString());
                        int n = this.xslTagMapper.insertSelective(xslTag);
                        if( n < 0 ){
                            logger.error(tag + "失败!");
                            return false;
                        }
                    }catch (Exception e){
                        logger.error(tag + "异常警报 :" + e.getMessage());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @SystemServiceLog(description = "标签更新Service")
    @Override
    public boolean UpdateXslClientLabel(XslTag[] xslTags) {
        String tag = "标签更新";
        if(xslTags != null){ //进行null的判断
            for(XslTag xslTag:xslTags){
                try{
                    if( xslTag != null ){//进行null的判断，最少要拥有id，和一个要修改的值
                        int n = this.xslTagMapper.updateByPrimaryKeySelective(xslTag);
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

    /**
     * 标签的删除
     * ①修改任务类别的state为0；
     * ②将与其相关的猎人、雇主、任务使用的标签进行删除
     * @author 王坤
     * @time 2018-8-12 20:33
     * @param xslTags
     * @return 是否成功
     */
    @SystemServiceLog(description = "标签删除Service")
    @Override
    public boolean deleteXslClientLabel(XslTag[] xslTags) {
        String tag = "标签删除";
        if(xslTags != null){ //进行null的判断
            for(XslTag xslTag:xslTags){
                try{
                    //不能为null 并且 不能已经被删除了
                    if( xslTag != null && !xslTag.getState() ){
                        //获取标签的id
                        int id = xslTag.getId();
                        xslTag.setState(false);
                        int n = xslTagMapper.updateByPrimaryKeySelective(xslTag);
                        if( n < 0 ){
                            logger.error(tag + "失败!");
                            return  false;
                        }
                        //1.①删除hunter_tag 中 tagId 为 id的元祖  ②状态为没被删除
                        XslHunterTagExample xslHunterTagExample = new XslHunterTagExample();
                        XslHunterTagExample.Criteria xslHunterTagExampleCriteria =  xslHunterTagExample.createCriteria();
                        xslHunterTagExampleCriteria.andTagidEqualTo(id);
                        xslHunterTagExampleCriteria.andStateEqualTo(true);
                        XslHunterTag xslHunterTag = new XslHunterTag();
                        xslHunterTag.setState(false);
                        xslHunterTagMapper.updateByExample(xslHunterTag,xslHunterTagExample);
                        //2.①删除masterr_tag 中 tagId 为 id的元祖  ②状态为没被删除
                        XslMasterTagExample xslMasterTagExample = new XslMasterTagExample();
                        XslMasterTagExample.Criteria xslMasterTagExampleCriteria = xslMasterTagExample.createCriteria();
                        xslMasterTagExampleCriteria.andMasteridEqualTo(id);
                        xslMasterTagExampleCriteria.andStateEqualTo(true);
                        XslMasterTag xslMasterTag = new XslMasterTag();
                        xslMasterTag.setState(false);
                        xslMasterTagMapper.updateByExampleSelective(xslMasterTag,xslMasterTagExample);
                        //3.删除task_tag 中 tagId 为 id的元祖  ②状态为没被删除
                        XslTaskTagExample xslTaskTagExample = new XslTaskTagExample();
                        XslTaskTagExample.Criteria xslTaskTagExampleCriteria = xslTaskTagExample.createCriteria();
                        xslTaskTagExampleCriteria.andTagidEqualTo(id);
                        xslTaskTagExampleCriteria.andStateEqualTo(true);
                        XslTaskTag xslTaskTag = new XslTaskTag();
                        xslTaskTag.setState(false);
                        xslTaskTagMapper.updateByExample(xslTaskTag,xslTaskTagExample);
                    }
                }catch (Exception e){
                    logger.error(tag + "异常警报 :" + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    @SystemServiceLog(description = "标签数量Service")
    @Override
    public List<MonitorNode> getTagCount() {
        XslTagExample example = new XslTagExample();
        List<XslTag> list = this.xslTagMapper.selectByExample(example);
        List<MonitorNode> listNode = new ArrayList<>();
        for(XslTag xslTag:list){
            MonitorNode monitorNode = new MonitorNode( xslTag.getUsenum().intValue() , xslTag.getName() );
            listNode.add(monitorNode);
        }
        return listNode;
    }
}
