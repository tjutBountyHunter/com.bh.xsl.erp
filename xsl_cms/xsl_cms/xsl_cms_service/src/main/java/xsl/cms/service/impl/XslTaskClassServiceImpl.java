package xsl.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xsl.cms.annotation.SystemServiceLog;
import xsl.cms.mapper.XslTaskCategoryMapper;
import xsl.cms.mapper.XslTaskMapper;
import xsl.cms.pojo.XslTask;
import xsl.cms.pojo.XslTaskCategory;
import xsl.cms.pojo.XslTaskCategoryExample;
import xsl.cms.pojo.XslTaskExample;
import xsl.cms.pojo.common.MonitorNode;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslTaskClassService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  xsltaskClass页面的服务层
 *  @author 王坤
 */
@Service
public class XslTaskClassServiceImpl implements XslTaskClassService {
    /* 任务类别日志 */
    Logger logger = LoggerFactory.getLogger(XslTaskClassServiceImpl.class);

    @Resource
    private XslTaskCategoryMapper xslTaskCategoryMapper;
    @Resource
    private XslTaskMapper xslTaskMapper;

    /**
     * 页面数据的查询
     * @param page 分页查询页数
     * @param rows 分页查询行数
     * @param key 任务ID
     * @param key1 任务状态
     * @return 返回一个数据的列表包含data和total
     */
    @SystemServiceLog(description = "任务类别分页查询Service")
    @Override
    public PageObject SelectTaskClassAll(Integer page, Integer rows,Integer key,Integer key1 ) {
        XslTaskCategoryExample example = new XslTaskCategoryExample();
        XslTaskCategoryExample.Criteria criteria = example.createCriteria();
        //进行判断防止程序崩溃
        if( key != null ){//任务ID
            criteria.andIdEqualTo(key);
        }
        if( key1 != null){//任务状态
            criteria.andParentidEqualTo(key1);
        }
        PageHelper.startPage(page,rows);//进行分页
        List<XslTaskCategory> list = this.xslTaskCategoryMapper.selectByExample(example);
        if( list != null ){
            PageObject object = new PageObject();
            object.setData(list);
            PageInfo<XslTaskCategory> info = new PageInfo<XslTaskCategory>(list);//得到分页的信息
            //得到分页的总数量
            object.setTotal(info.getTotal());
            return object;
        }
        return null;//回来使用工具结果类
    }

    @SystemServiceLog(description = "任务类别添加Service")
    @Override
    public boolean InsertXslTaskClass(XslTaskCategory[] xslTaskCategories) {
        String tag = "任务类别添加";
        //进行添加，查看是否是null
        if( xslTaskCategories != null ){ //判断不等于null
            for(XslTaskCategory xslTaskCategory:xslTaskCategories){
                try{
                    if( xslTaskCategory != null ){ //判断不等于null
                        //时间格式
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String date = sdf.format(new Date());
                        //设置创建时间
                        xslTaskCategory.setCreatedate(date);
                        int n = this.xslTaskCategoryMapper.insertSelective(xslTaskCategory);
                        if( n < 0 ){
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

    @SystemServiceLog(description = "任务类别更新Service")
    @Override
    public boolean UpdateXslTaskClass(XslTaskCategory[] xslTaskCategories) {
        String tag = "任务类别更新";
        if(xslTaskCategories != null){ //进行null的判断
            for(XslTaskCategory xslTaskCategory:xslTaskCategories){
                try{
                    if( xslTaskCategory != null ){//进行null的判断，最少要拥有id，和一个要修改的值
                        int n = this.xslTaskCategoryMapper.updateByPrimaryKeySelective(xslTaskCategory);
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
     * 任务类别的批量删除
     * ①修改任务类别的state为0；
     * ②以该id为父类的类别一并删除（递归）
     * ③更改存在任务并且以此类型以及子类型的任务的id换成该类型的父类型（如：0）；
     * @author 王坤
     * @time 2018-8-12 18:48
     * @param xslTaskCategories
     * @return
     */
    @SystemServiceLog(description = "任务类别删除Service")
    @Override
    public boolean deleteXslTaskClass(XslTaskCategory[] xslTaskCategories) {
        String tag = "任务类别删除";
        if(xslTaskCategories != null){ //进行null的判断
            for(XslTaskCategory xslTaskCategory:xslTaskCategories){
                try{
                    boolean success = deleteOneXslTaskClass(xslTaskCategory,xslTaskCategory.getParentid());
                    if( !success ){
                        logger.error(tag + "失败!");
                        return false;
                    }
                }catch (Exception e){
                    logger.error(tag + "异常警报  :" + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 删除的单个操作,并且赋予最高父类的id
     * 更改以其id为任务类型的任务的 任务类型为最高parentId
     * @param xslTaskCategory
     * @return
     */
    public boolean deleteOneXslTaskClass(XslTaskCategory xslTaskCategory,int superId){
        try {
            //不为null 并且 没有被删除
            if( xslTaskCategory != null && !xslTaskCategory.getState() ){
                //1.进行状态的逻辑删除false
                xslTaskCategory.setState(false);
                int n = this.xslTaskCategoryMapper.updateByPrimaryKeySelective(xslTaskCategory);
                //操作失败
                if( n < 0 ){
                    System.out.println("xsl_task_class任务类型删除失败！");
                    return false;
                }
                //2.以该id为父类的类别一并删除
                int xslTaskCategoryId = xslTaskCategory.getId();
                deleteXslTaskByParentId(xslTaskCategoryId,superId);
                //3.更改存在任务并且以此类型以及子类型的任务的id换成该类型的父类型（如：0）
                //要被更新的任务的条件
                XslTaskExample example = new XslTaskExample();
                XslTaskExample.Criteria criteria = example.createCriteria();
                //设置任务id
                criteria.andTasktypeEqualTo(xslTaskCategoryId);
                //只修改没有审核，正在完成，正在待接的任务
                List<Byte> stateList = new ArrayList<>();
                //待接收
                stateList.add((byte)(0));
                //启动推荐算法待接收
                stateList.add((byte)(1));
                //进行中
                stateList.add((byte)(2));
                //待审核
                stateList.add((byte)(4));
                criteria.andStateIn(stateList);
                //要更新的内容
                XslTask xslTask = new XslTask();
                xslTask.setTasktype(xslTaskCategoryId);
                xslTaskMapper.updateByExampleSelective(xslTask,example);
                return true;
            }
        }catch (Exception e){
            logger.error("单个任务类别删除异常警报 :" + e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * 将父类id为parentId的任务类别以及其子类别全部删除
     * @param parentId
     */
    public void deleteXslTaskByParentId(int parentId,int superId){
        try{
            //获取到父类id为parentId的任务类别的id
            XslTaskCategoryExample example = new XslTaskCategoryExample();
            XslTaskCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentidEqualTo(parentId);
            List<XslTaskCategory> categoriesList = this.xslTaskCategoryMapper.selectByExample(example);
            //遍历拥有父类id为parentID的id
            for( XslTaskCategory xslTaskCategory : categoriesList ){
                //没有被删除的情况下，进行删除
                if( xslTaskCategory.getState() ) {
                    deleteOneXslTaskClass(xslTaskCategory,superId);
                }
            }
        }catch (Exception e){
            logger.error("任务极其子任务删除异常警报 :" + e.getMessage());
        }
    }

    @SystemServiceLog(description = "雇主类别数量Service")
    @Override
    public List<MonitorNode> monitorTaskClassCount() {
        XslTaskCategoryExample example = new XslTaskCategoryExample();
        List<XslTaskCategory> list = this.xslTaskCategoryMapper.selectByExample(example);
        List<MonitorNode> listNode = new ArrayList<>();
        for(XslTaskCategory xslTaskCategory:list){
            MonitorNode monitorNode = new MonitorNode(xslTaskCategory.getTasknum(),xslTaskCategory.getName());
            listNode.add(monitorNode);
        }
        return listNode;
    }
}
