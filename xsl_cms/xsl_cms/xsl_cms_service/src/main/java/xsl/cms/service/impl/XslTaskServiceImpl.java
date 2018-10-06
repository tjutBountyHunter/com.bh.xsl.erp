package xsl.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.cms.mapper.XslTaskCategoryMapper;
import com.xsl.cms.mapper.XslTaskMapper;
import com.xsl.cms.mapper.XslTaskTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xsl.cms.Utils.DateUtils;
import xsl.cms.annotation.SystemServiceLog;
import xsl.cms.pojo.*;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslTaskService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *  xsl_user页面的服务层
 *  @author 王坤
 */
@Service
public class XslTaskServiceImpl implements XslTaskService {
    /* 任务服务 */
    Logger logger = LoggerFactory.getLogger(XslTaskServiceImpl.class);

    @Resource
    private XslTaskMapper xslTaskMapper;
    @Resource
    private XslTaskCategoryMapper xslTaskCategoryMapper;
    @Resource
    private XslTaskTagMapper xslTaskTagMapper;

    /**
     * 页面数据的查询
     * @param page 分页查询页数
     * @param rows 分页查询行数
     * @param key 任务ID
     * @param key1 任务状态
     * @return 返回一个数据的列表包含data和total
     */
    @SystemServiceLog(description = "任务分页查询Service")
    @Override
    public PageObject SelectTaskAll(Integer page, Integer rows,Integer key,Byte key1 ) {
        String tag = "任务分页查询";
        PageObject object = new PageObject();
        try{
            XslTaskExample example = new XslTaskExample();
            XslTaskExample.Criteria criteria = example.createCriteria();
            //进行判断防止程序崩溃
            if( key != null ){//任务ID
                criteria.andIdEqualTo(key);
            }
            if( key1 != null){//任务状态
                criteria.andStateEqualTo(key1);
            }
            PageHelper.startPage(page,rows);//进行分页
            List<XslTask> list = this.xslTaskMapper.selectByExample(example);
            DateUtils.setDateInPojo(list);
            object.setData(list);
            PageInfo<XslTask> info = new PageInfo<XslTask>(list);//得到分页的信息
            //得到分页的总数量
            object.setTotal(info.getTotal());
        }catch (Exception e){
            logger.error(tag + "异常警报 :" + e.getMessage());
        }finally {
            return object;
        }
    }

    /**
     * 任务的插入
     * 只有状态为4和0（未审核、待接收）的时候才被创建
     * @author-王坤
     * @time 2018-8-11 22:36
     * @param xslTasks
     * @return
     */
    @SystemServiceLog(description = "任务分页添加Service")
    @Override
    public boolean InsertXslTask(XslTask[] xslTasks) {
        String tag = "任务分页添加";
        //进行添加，查看是否是null
        if( xslTasks != null ){ //判断不等于null
            for(XslTask xslTask:xslTasks){
                try{
                    if( xslTask != null ){ //判断不等于null
                        Byte state = xslTask.getState();
                        //只有状态为4的时候才能被创建
                        if( state == null || state == 4 || state == 0 ){
                            //设置创建时间
                            xslTask.setCreatedate(DateUtils.getDateToString());
                            //设置修改时间
                            xslTask.setUpdatedate(DateUtils.getDateToString());
                            //设置最后一次接受任务的时间
                            xslTask.setDeadline(DateUtils.getDateTimeToString());
                            xslTask.setCid(1);
                            int n = this.xslTaskMapper.insertSelective(xslTask);
                            if( n < 0 ){
                                return false;
                            }
                            //如果是待接收的话，在任务类型进行数量的++
//                            Integer tasktype = xslTask.getTasktype();
                            if(true){
                                //1.获取任务的类型id
                                XslTaskCategoryExample example = new XslTaskCategoryExample();
                                XslTaskCategoryExample.Criteria criteria = example.createCriteria();
//                                criteria.andIdEqualTo(xslTask.getTasktype());
                                List<XslTaskCategory> list = xslTaskCategoryMapper.selectByExample(example);
                                //看看有没有这个任务类型，如果有就++
                                if( list.size() > 0 ){
                                    XslTaskCategory xslTaskCategory = list.get(0);
                                    //任务数量++
                                    xslTaskCategory.setTasknum(xslTaskCategory.getTasknum() + 1);
                                }
                            }
                        }else{
                            logger.error("任务创建失败 ，初始状态只能是待接收和待审核！");
                            return false;
                        }
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
     * 任务的更新
     * @author-王坤
     * @time 2018-8-11 22:47
     * @param xslTasks
     * @return
     */
    @SystemServiceLog(description = "任务更新Service")
    @Override
    public boolean UpdateXslTask(XslTask[] xslTasks) {
        String tag = "任务更新";
        if(xslTasks != null){ //进行null的判断
            for(XslTask xslTask:xslTasks){
                try{
                    if( xslTask != null ){//进行null的判断，最少要拥有id，和一个要修改的值
                        xslTask.setUpdatedate(DateUtils.getDateTimeToString());//设置修改时间
                        int n = this.xslTaskMapper.updateByPrimaryKeySelective(xslTask);
                        if(n < 0){
                            logger.error(tag + "是失败!");
                            return false;
                        }
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
     * 任务的删除
     * ①任务逻辑删除 -1：冻结
     * ②将任务有关的标签进行逻辑删除    表xsl_task_tag
     * @author-王坤
     * @time 2018-8-11 22:48
     * @param xslTasks
     * @return
     */
    @SystemServiceLog(description = "任务删除Service")
    @Override
    public boolean deleteXslTask(XslTask[] xslTasks) {
        String tag = "任务删除";
        if(xslTasks != null){ //进行null的判断
            for(XslTask xslTask:xslTasks){
               try{
                   if( xslTask != null ){//进行null的判断，最少要拥有id，和一个要修改的值
                       delXslTask(xslTask);
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
     * 单任务删除
     * @param xslTask
     * @return
     */
    @Override
    public boolean delXslTask(XslTask xslTask) {
        if (xslTask != null) {
            //进行null的判断，最少要拥有id，和一个要修改的值
            //1.xslUser的逻辑删除
            xslTask.setUpdatedate(DateUtils.getDateToString());//设置修改时间
            xslTask.setState((byte) (-1));//-1代表冻结的意思，进行逻辑删除
            int n = this.xslTaskMapper.updateByPrimaryKeySelective(xslTask);
            if (n < 0) {
                System.out.println("xsl_task 任务删除失败！");
                return false;
            }
            //2.进行任务相关的标签删除
            XslTaskTag xslTaskTag = new XslTaskTag();
            xslTaskTag.setState(false);
            //进行任务id为此逻辑删除id的任务标签全部删除
            XslTaskTagExample example = new XslTaskTagExample();
            XslTaskTagExample.Criteria criteria = example.createCriteria();
            criteria.andTaskidEqualTo(xslTask.getId());
            int updatecount = this.xslTaskTagMapper.updateByExampleSelective(xslTaskTag, example);
        }
        return true;
    }

    /**
     * 任务审核
     * ①审核成功：state：0（待接收）
     * ②审核失败：state：-4（审核未通过）
     * @author-王坤
     * @time 2018-8-12 18:31
     * @param xslTask
     * @return
     */
    @SystemServiceLog(description = "任务审核Service")
    @Override
    public boolean approve(XslTask xslTask) {
        String  tag = "任务审核";
        try{
            //防止空指针异常
            if( xslTask != null ){
                xslTask.setUpdatedate(DateUtils.getDateToString());//设置修改时间
                int n = this.xslTaskMapper.updateByPrimaryKeySelective(xslTask);
                if( n < 0 ) {
                    logger.error(tag + "失败!");
                    return false;
                }
            }
        }catch (Exception e){
            logger.error(tag + "异常警报 :" + e.getMessage());
        }
        return true;
    }

    /**
     * user_approve页面的数据显示，只查询状态为state:4 (未审核)的任务
     * @param page 分页的页数
     * @param rows 分页的行数
     * @return 返回页面显示的数据的集合
     */
    @SystemServiceLog(description = "任务审核分页查询Service")
    @Override
    public PageObject SelectTaskApprove(Integer page, Integer rows) {
        return SelectTaskAll(page,rows,null,(byte)(4));//只查询待接收的任务
    }

    /**
     * 进行中的任务的总数量
     * @author 王坤
     * @return 任务数量
     */
    @SystemServiceLog(description = "任务进行总数查询Service")
    @Override
    public Integer taskingSum() {
        XslTaskExample example = new XslTaskExample();
        XslTaskExample.Criteria criteria = example.createCriteria();
        //进行中的任务状态
        criteria.andStateEqualTo((byte)(2));
        int n = this.xslTaskMapper.countByExample(example);
        return n;
    }

    /**
     * 任务成功的总数
     * @author 王坤
     * @return 成功总数
     */
    @SystemServiceLog(description = "任务成功总数查询Service")
    @Override
    public Integer taskSuccessSum() {
        XslTaskExample example = new XslTaskExample();
        XslTaskExample.Criteria criteria = example.createCriteria();
        //进行中的任务状态
        criteria.andStateEqualTo((byte)(3));
        int n = this.xslTaskMapper.countByExample(example);
        return n;
    }

    /**
     * 任务失败的总数
     * @author 王坤
     * @return 失败总数
     */
    @SystemServiceLog(description = "任务失败总数查询Service")
    @Override
    public Integer taskFailSum() {
        XslTaskExample example = new XslTaskExample();
        XslTaskExample.Criteria criteria = example.createCriteria();
        //进行中的任务状态
        criteria.andStateEqualTo((byte)(-2));
        int n = this.xslTaskMapper.countByExample(example);
        return n;
    }

    /**
     * 任务待接收数量总数（待接收和推荐待接任务）的监控
     * @author 王坤
     * @return 任务待接总数量
     */
    @SystemServiceLog(description = "任务待接总数查询Service")
    @Override
    public Integer taskWaitSum() {
        XslTaskExample example = new XslTaskExample();
        XslTaskExample.Criteria criteria = example.createCriteria();
        List<Byte> states = new ArrayList<Byte>();
        //待接收的任务
        states.add((byte)(0));
        //推荐待接的任务
        states.add((byte)(1));
        criteria.andStateIn(states);
        int n = this.xslTaskMapper.countByExample(example);
        return n;
    }
}
