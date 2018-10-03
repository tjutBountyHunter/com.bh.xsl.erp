package xsl.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.cms.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xsl.cms.Utils.DateUtils;
import xsl.cms.annotation.SystemServiceLog;
import xsl.cms.pojo.*;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslTaskService;
import xsl.cms.service.XslUserService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  xsl_user页面的服务层
 *  @author 王坤
 */
@Service
@Transactional
public class XslUserServiceImpl implements XslUserService {

    /* 用户的日志记录 */
    private Logger logger = LoggerFactory.getLogger(XslUserService.class);

    /* 用户dao管理 */
    @Resource
    private XslUserMapper xslUserMapper;

    /* 猎人dao管理 */
    @Resource
    private XslHunterMapper xslHunterMapper;

    /* 雇主dao管理 */
    @Resource
    private XslMasterMapper xslMasterMapper;

    /* 猎人标签管理dao */
    @Resource
    private XslHunterTagMapper xslHunterTagMapper;

    /* 任务管理dao */
    @Resource
    private XslTaskMapper xslTaskMapper;

    /* 雇主标签管理dao */
    @Resource
    private XslMasterTagMapper xslMasterTagMapper;

    @Resource
    private XslSchoolinfoMapper xslSchoolinfoMapper;

    /**
     * 页面数据的查询
     *
     * @param page 分页查询页数
     * @param rows 分页查询行数
     * @param key 用户ID
     * @param key1 用户状态
     * @return 返回一个数据的列表包含data和total
     */
    @Override
    @SystemServiceLog(description = "用户查询Service")
    public PageObject SelectUserAll(Integer page, Integer rows,Integer key,Byte key1 ) {
        XslUserExample example = new XslUserExample();
        PageObject object = new PageObject();
        try{
            XslUserExample.Criteria criteria = example.createCriteria();
            /* 进行判断防止程序崩溃，用户ID */
            if( key != null ){
                criteria.andIdEqualTo(key);
            }
            if( key1 != null){//用户状态
                criteria.andStateEqualTo(key1);
            }
            criteria.andStateGreaterThan((byte) -3);

            /* 进行分页 */
            PageHelper.startPage(page,rows);
            List<XslUser> list = this.xslUserMapper.selectByExample(example);
            object.setData(list);
            /* 得到分页的信息 */
            PageInfo<XslUser> info = new PageInfo<XslUser>(list);
            /* 得到分页的总数量 */
            object.setTotal(info.getTotal());
        }catch (Exception e){
            logger.error("用户分页查询异常报警！" + e.getMessage());
            object = null;
        }finally {
            return object;
        }
    }

    /**
     * 插入新用户，生成hunterID、masterID并且更新XslUser操作(批量操作)
     *
     * 根据状态来决定后面的业务逻辑
     * ①State=0(未审核) || 为null使用数据库默认 :不进行雇主和猎人的生成，当审核通过的时候在进行id的修改
     * ②State=1(正常) :正常进行雇主和猎人的生成，猎人和雇主的id都是通过mybatis特性对id进行映射
     * ③State:-1(冻结、-2审核未通过)冻结，打印出错误，直接插入
     * @author 王坤
     * @time 2018-8-11 21:34
     * @param xslUsers 插入的数据
     * @return 是否插入成功
     */
    @Override
    @SystemServiceLog(description = "用户添加Service")
    public boolean InsertXslUser(XslUser[] xslUsers) {
            /* 进行添加，查看是否是null */
            /* 判断不等于null */
            if(xslUsers != null){
                for(XslUser xslUser:xslUsers){
                    /* 判断不等于null */
                    if(xslUser != null){
                        try {
                            Byte state = xslUser.getState();
                            if(state == null){
                                state = (byte)(0);
                            }
                            //设置创建时间
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String date = sdf.format(new Date());
                            xslUser.setCreatedate(date);
                            xslUser.setUpdatedate(date);
                            /* 状态为1正常的时候进行创建猎人和雇主的id */
//                            if (state == 1) {
//                                /* 获取已经生成好的雇主和猎人id */
//                                xslUser = getXslUser(xslUser, new Date());
//                            }
                            /* 其他情况直接创建，也没有什么操作 */
                            xslUser.setHunterid(setHunterAndGetHunterId(xslUser.getPhone()));
                            xslUser.setMasterid(setMasterAndGetMasterId(xslUser.getPhone()));
                            xslUser.setSchoolinfo(setSchoolInfoAndGetSchoolInfo());
                            int n2 = this.xslUserMapper.insertSelective(xslUser);
                            /* 操作不成功 */
                            System.out.println(n2);
                            if (n2 < 0) {
                                logger.error("xslUserService 中，该条记录插入失败！");
                            }
                        }catch (Exception e){
                            logger.error("User用户删除异常警告" + e.getMessage());
                            return false;
                        }
                    }
                }
            }
            return true;
    }

    /**
     * 更新页面数据，只更新修改过的数据和更新时间（批量操作）
     *
     * @author 王坤
     * @time 2018-8-11 21:56
     * @param xslUsers 携带更新数据的pojo
     * @return 是否更新成功
     */
    @SystemServiceLog(description = "用户更新Service")
    @Override
    public boolean UpdateXslUser(XslUser[] xslUsers) {
        if(xslUsers != null){ //进行null的判断
            for(XslUser xslUser:xslUsers){
                try {
                    if( xslUser != null ){//进行null的判断，最少要拥有id，和一个要修改的值
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String date = sdf.format(new Date());
                        xslUser.setUpdatedate(date);//设置修改时间
                        int n = this.xslUserMapper.updateByPrimaryKeySelective(xslUser);
                        /* 一条用户更新失败 */
                        if(n < 0){
                            logger.error("用户更新失败！" );
                        }
                    }
                }catch (Exception e){
                    logger.error("用户更新异常报警 "  +  e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 删除数据的操作
     *
     * ①对用户进行逻辑删除，state=-1
     * ②进行更新时间的更改
     * ③对 对应的雇主和猎人进行逻辑删除state=0
     * ④对 猎人来说删除与猎人相关的接受任务和标签
     * ⑤对 雇主来说删除与雇主相关的发布的任务和标签
     * @author 王坤
     * @time 2018-8-11 21:56
     * @param xslUsers 要删除的数据，只是逻辑上的删除
     * @return 是否删除成功
     */
    @SystemServiceLog(description = "用户删除Service")
    @Override
    public boolean deleteXslUser(XslUser[] xslUsers) {
        /* 进行null的判断 */
        if(xslUsers != null){
            for(XslUser xslUser:xslUsers){
                try{
                    /* 进行null的判断，最少要拥有id，和一个要修改的值 */
                    if( xslUser != null ){
                        /* hunter的逻辑删除 */
                        XslHunter xslHunter = new XslHunter();
                        xslHunter.setId(xslUser.getHunterid());
                        xslHunter.setState(false);
                        int n = this.xslHunterMapper.updateByPrimaryKeySelective(xslHunter);
                        /* hunter的接受任务的删除,由于没有任务中没有hunterid属性，所以回来再写 */

                        /* hunter的标签的删除 */
                        XslHunterTagExample example = new XslHunterTagExample();
                        XslHunterTagExample.Criteria criteria = example.createCriteria();
                        criteria.andHunteridEqualTo(xslHunter.getId());
                        XslHunterTag xslHunterTag = new XslHunterTag();
                        xslHunterTag.setState(false);
                        /* 这里不用判断，因为有可能没有标签 */
                        this.xslHunterTagMapper.updateByExampleSelective(xslHunterTag,example);

                        /* master的逻辑删除 */
                        XslMaster xslMaster = new XslMaster();
                        xslMaster.setId(xslUser.getMasterid());
                        xslMaster.setState(false);
                        int n1 = this.xslMasterMapper.updateByPrimaryKeySelective(xslMaster);

                        /* 任务极其标签的逻辑删除 */
                        XslTaskExample xslTaskExample = new XslTaskExample();
                        XslTaskExample.Criteria xslTaskExampleCriteria =  xslTaskExample.createCriteria();
                        xslTaskExampleCriteria.andSendidEqualTo(xslMaster.getId());
                        List<Byte> byteList = new ArrayList<>();
                        /* 待接收 */
                        byteList.add((byte)(0));
                        /* 启动推荐算法待接收 */
                        byteList.add((byte)(1));
                        /* 进行中 */
                        byteList.add((byte)(2));
                        /* 待审核 */
                        byteList.add((byte)(4));
                        xslTaskExampleCriteria.andStateIn(byteList);
                        List<XslTask> tasklist = this.xslTaskMapper.selectByExample(xslTaskExample);
                        XslTask[] xslTasks = null;
                        if( tasklist == null ){
                            xslTasks =  (XslTask[]) tasklist.toArray();
                        }
                        XslTaskService xslTaskService = new XslTaskServiceImpl();
                        xslTaskService.deleteXslTask(xslTasks);
                        /* master标签的逻辑删除 */
                        XslMasterTagExample xslMasterTagExample = new XslMasterTagExample();
                        XslMasterTagExample.Criteria xslMasterCriteria = xslMasterTagExample.createCriteria();
                        xslMasterCriteria.andMasteridEqualTo(xslUser.getMasterid());
                        XslMasterTag xslMasterTag = new XslMasterTag();
                        xslMasterTag.setState(false);
                        this.xslMasterTagMapper.updateByExampleSelective(xslMasterTag,xslMasterTagExample);
                        /* xslUser的逻辑删除 */
                        /* 设置修改时间 */
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String date = sdf.format(new Date());
                        xslUser.setUpdatedate(date);
                        /* -1代表冻结的意思，进行逻辑删除 */
                        xslUser.setState((byte)(-3));
                        int n2 = this.xslUserMapper.updateByPrimaryKeySelective(xslUser);
                        if(n < 0 && n1 < 0 && n2 < 0){
                            logger.error("用户删除失败！");
                        }
                    }
                }catch (Exception e){
                    logger.error("用户删除异常报警 " + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * user_approve页面的数据显示，只查询状态为0未审核的用户
     *
     * @author 王坤
     * @time 2018-8-12 18:17
     * @param page 分页的页数
     * @param rows 分页的行数
     * @return 返回页面显示的数据的集合
     */
    @SystemServiceLog(description = "用户审核分页Service")
    @Override
    public PageObject SelectUserApprove(Integer page, Integer rows) {
        return SelectUserAll(page,rows,null,(byte)(0));//只查询未经过审核的
    }

    /**
     * user_approve页面的审核操作
     *
     * ①审核成功：state状态为1，并且要就行时间的更新，并且创建雇主和猎人id
     * ②审核失败：state状态为-2，并且进行时间的更新
     * @param xslUser
     * @return
     */
    @SystemServiceLog(description = "用户审核操作Service")
    @Override
    public boolean approve(XslUser xslUser) {
        try {
            //防止空指针异常
            if( xslUser != null ){
                Byte state = xslUser.getState();
                //审核成功
                if( state == 1 ){
                    xslUser = getXslUser(xslUser,new Date());
                }else{
                    //审核失败
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(new Date());
                    xslUser.setUpdatedate(date);
                }
                int n = this.xslUserMapper.updateByPrimaryKeySelective(xslUser);
                if( n < 0 ){return false;}
            }
        }catch (Exception e){
            logger.error("用户审核操作 " + e.getMessage() );
            return false;
        }
        return true;
    }

    @SystemServiceLog(description = "用户总数查询Service")
    @Override
    public Integer countUser()  {
        XslUserExample example = new XslUserExample();
        Integer count = 0;
        try{
            count  = this.xslUserMapper.countByExample(example);
            if( count == null || count < 0 ){//防止越界和空指针
                logger.error("用户总数查询越界或者空指针!" );
                count = 0;
            }
        }catch(Exception e){
            logger.error("用户总数查询异常警报  :" + e.getMessage());
            count = 0;
        } finally{
            return count;
        }
    }

    /**
     * 一个进行猎人id和雇主id自动生成的XSLUser用户的方法，便于其他方法中使用
     * @author 王坤
     * @time 2018-8-11 21:51
     * @param xslUser
     * @return
     */
    private XslUser getXslUser(XslUser xslUser,Date date){
        //生成hunterID
        XslHunter xslHunter = new XslHunter();
        try {
            xslHunter.setDescr(" ");//防止注入失败
            xslHunter.setLasttime(DateUtils.getDateToString());//由于没有0000-00-00所以设置一下时间
            this.xslHunterMapper.insertSelective(xslHunter);
            //生成masterID
            XslMaster xslMaster = new XslMaster();
            xslMaster.setDescr(" ");//防止注入失败
            xslMaster.setLastaccdate(DateUtils.getDateToString());
            this.xslMasterMapper.insertSelective(xslMaster);
            //设置hunterID
            xslUser.setHunterid(xslHunter.getId());
            //设置masterID
            xslUser.setMasterid(xslMaster.getId());
            //设置修改时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date1 = sdf.format(new Date());
            xslUser.setUpdatedate(date1);
        }catch (Exception e){
            logger.error("雇主、猎人账户生成异常  :" + e.getMessage());
            return null;
        }
        return xslUser;
    }

    private XslSchoolinfo mockXslSchoolinfo(){
        XslSchoolinfo schoolinfo = new XslSchoolinfo();
        schoolinfo.setSno("20150001");
        schoolinfo.setMajor("计算机科学与技术");
        schoolinfo.setCollege("计算机科学与工程学院");
        schoolinfo.setSchool("天津理工大学");
        schoolinfo.setStartdate("2015-09");
        schoolinfo.setDegree((byte)2);
        schoolinfo.setSchoolhours((byte)4);
        return schoolinfo;
    }

    private XslMaster mockXslMaster(String phone){
        XslMaster xslMaster = new XslMaster();
        xslMaster.setLevel((short)0);
        xslMaster.setEmpirical(0);
        xslMaster.setTaskaccnum(0);
        xslMaster.setTaskrevokenum(0);
        xslMaster.setCredit((short)100);
        xslMaster.setDescr("雇主" + phone);
        xslMaster.setState(true);
        xslMaster.setLastaccdate(DateUtils.getDateToString());
        return xslMaster;
    }

    private XslHunter mockXslHunter(String phone){
        XslHunter xslHunter = new XslHunter();
        xslHunter.setLevel((short)0);
        xslHunter.setEmpirical(0);
        xslHunter.setTaskaccnum(0);
        xslHunter.setTaskfailnum(0);
        xslHunter.setCredit((short)100);
        xslHunter.setDescr("猎人" + phone);
        xslHunter.setState(true);
        xslHunter.setLasttime(DateUtils.getDateToString());
        return xslHunter;
    }

    private int setHunterAndGetHunterId(String phone){
        XslHunter xslHunter = mockXslHunter(phone);
        xslHunterMapper.insertSelective(xslHunter);
        XslHunterExample example = new XslHunterExample();
        XslHunterExample.Criteria criteria = example.createCriteria();
        criteria.andDescrEqualTo("猎人" + phone);
        List<XslHunter> xslHunters = xslHunterMapper.selectByExample(example);
        return xslHunters.get(0).getId();
    }

    private int setMasterAndGetMasterId(String phone){
        XslMaster xslMaster = mockXslMaster(phone);
        xslMasterMapper.insertSelective(xslMaster);
        XslMasterExample example = new XslMasterExample();
        XslMasterExample.Criteria criteria = example.createCriteria();
        criteria.andDescrEqualTo("雇主" + phone);
        List<XslMaster> xslMasters = xslMasterMapper.selectByExample(example);
        return xslMasters.get(0).getId();
    }

    private int setSchoolInfoAndGetSchoolInfo(){
        XslSchoolinfo schoolinfo = mockXslSchoolinfo();
        xslSchoolinfoMapper.insertSelective(schoolinfo);
        XslSchoolinfoExample example = new XslSchoolinfoExample();
        XslSchoolinfoExample.Criteria criteria = example.createCriteria();
        criteria.andSnoEqualTo(schoolinfo.getSno());
        List<XslSchoolinfo> xslSchoolinfos = xslSchoolinfoMapper.selectByExample(example);
        return xslSchoolinfos.get(0).getId();
    }



}
