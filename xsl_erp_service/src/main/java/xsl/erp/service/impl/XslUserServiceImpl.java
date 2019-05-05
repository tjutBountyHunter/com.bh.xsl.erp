package xsl.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.erp.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import vo.UserApproveRes;
import xsl.erp.Utils.DateUtils;
import xsl.erp.annotation.SystemServiceLog;
import xsl.erp.pojo.*;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslTaskService;
import xsl.erp.service.XslUserService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *  xsl_user页面的服务层
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
     * @param phone 用户ID
     * @param state 用户状态
     * @return 返回一个数据的列表包含data和total
     */
    @Override
    @SystemServiceLog(description = "用户查询Service")
    public PageObject selectUserAll(Integer page, Integer rows, String phone, Byte state) {
        XslUserExample example = new XslUserExample();
        PageObject result = new PageObject();
        try{
            XslUserExample.Criteria criteria = example.createCriteria();
            /* 进行判断防止程序崩溃，用户ID */
            if(!StringUtils.isEmpty(phone)){
                criteria.andPhoneEqualTo(phone);
            }

            if( state != null){//用户状态
                criteria.andStateEqualTo(state);
            }

            criteria.andStateGreaterThan((byte) -3);

            /* 进行分页 */
            PageHelper.startPage(page,rows);
            List<XslUser> userList = this.xslUserMapper.selectByExample(example);
            result.setData(userList);

            /* 得到分页的信息 */
            PageInfo<XslUser> info = new PageInfo<XslUser>(userList);
            /* 得到分页的总数量 */
            result.setTotal(info.getTotal());

        }catch (Exception e){
            logger.error("用户分页查询异常报警！" + e.getMessage());
        }

        return result;
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
    public boolean insertXslUser(List<XslUser> xslUsers) {
            /* 进行添加，查看是否是null */
            /* 判断不等于null */
            if(xslUsers == null || xslUsers.size() < 1){
                return false;
            }

            for(XslUser xslUser:xslUsers){
                try {
                    xslUser.setUserid(UUID.randomUUID().toString());
                    Byte state = xslUser.getState();
                    if(state == null){
                        xslUser.setState((byte) 0);
                    }
                    //设置创建时间
                    xslUser.setCreatedate(new Date());
                    xslUser.setUpdatedate(new Date());


                    /* 其他情况直接创建，也没有什么操作 */
                    xslUser.setHunterid(setHunterAndGetHunterId(xslUser.getPhone()));
                    xslUser.setMasterid(setMasterAndGetMasterId(xslUser.getPhone()));
                    xslUser.setSchoolinfo(setSchoolInfoAndGetSchoolInfo());

                    int n2 = this.xslUserMapper.insertSelective(xslUser);

                    if (n2 < 0) {
                        logger.error("xslUserService 中，该条记录插入失败:{}", xslUser.getUserid());
                        return false;
                    }
                }catch (Exception e){
                    logger.error("User用户删除异常警告" + e.getMessage());
                    return false;
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
    public boolean updateXslUser(List<XslUser> xslUsers) {
        if(xslUsers != null){ //进行null的判断
            for(XslUser xslUser:xslUsers){
                try {
                    if( xslUser != null ){//进行null的判断，最少要拥有id，和一个要修改的值
                        xslUser.setUpdatedate(new Date());//设置修改时间
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
    public boolean deleteXslUser(List<XslUser> xslUsers) {
        /* 进行null的判断 */
        if(xslUsers == null || xslUsers.size() < 1){
            return false;
        }

        for(XslUser xslUser:xslUsers){
            try{
                /* 进行null的判断，最少要拥有id，和一个要修改的值 */
                if( xslUser != null ){
                    /* hunter的逻辑删除 */
                    XslHunter xslHunter = new XslHunter();
                    xslHunter.setHunterid(xslUser.getHunterid());
                    xslHunter.setState(false);
                    int n = xslHunterMapper.updateByHunterIdSelective(xslHunter);
                    /* hunter的接受任务的删除,由于没有任务中没有hunterid属性，所以回来再写 */

                    /* hunter的标签的删除 */
                    XslHunterTagExample example = new XslHunterTagExample();
                    XslHunterTagExample.Criteria criteria = example.createCriteria();
                    criteria.andHunteridEqualTo(xslHunter.getHunterid());
                    XslHunterTag xslHunterTag = new XslHunterTag();
                    xslHunterTag.setState(false);
                    /* 这里不用判断，因为有可能没有标签 */
                    xslHunterTagMapper.updateByExampleSelective(xslHunterTag,example);

                    /* master的逻辑删除 */
                    XslMaster xslMaster = new XslMaster();
                    xslMaster.setMasterid(xslUser.getMasterid());
                    xslMaster.setState(false);
                    int n1 = xslMasterMapper.updateByMasterIdSelective(xslMaster);

                    /* 任务极其标签的逻辑删除 */
                    XslTaskExample xslTaskExample = new XslTaskExample();
                    XslTaskExample.Criteria xslTaskExampleCriteria =  xslTaskExample.createCriteria();
                    xslTaskExampleCriteria.andSendidEqualTo(xslMaster.getMasterid());
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
                    List<XslTask> tasklist = xslTaskMapper.selectByExample(xslTaskExample);
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
                    xslUser.setUpdatedate(new Date());
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

        return true;
    }

    /**
     * user_approve页面的数据显示，只查询状态为0未审核的用户
     *
     * @param page 分页的页数
     * @param rows 分页的行数
     * @return 返回页面显示的数据的集合
     */
    @SystemServiceLog(description = "用户审核分页Service")
    @Override
    public PageObject SelectUserApprove(Integer page, Integer rows) {
		PageObject pageObject = selectUserAll(page, rows, null, (byte) (2));//只查询未经过审核的
		List<XslUser> userList = (List<XslUser>) pageObject.getData();

//		userList.stream().map();
		List<UserApproveRes> userApproveResList = new ArrayList<>(10);
		for (XslUser xslUser : userList){
			UserApproveRes userApproveRes = new UserApproveRes();
			BeanUtils.copyProperties(xslUser, userApproveRes);
			String schoolId = xslUser.getSchoolinfo();


			if(!StringUtils.isEmpty(schoolId)){
                XslSchoolinfoExample xslSchoolinfoExample = new XslSchoolinfoExample();
                XslSchoolinfoExample.Criteria criteria = xslSchoolinfoExample.createCriteria();
                criteria.andSchoolidEqualTo(schoolId);
                List<XslSchoolinfo> xslSchoolinfos = xslSchoolinfoMapper.selectByExample(xslSchoolinfoExample);
                if(xslSchoolinfos != null && xslSchoolinfos.size() > 0){
                    XslSchoolinfo xslSchoolinfo = xslSchoolinfos.get(0);
                    BeanUtils.copyProperties(xslSchoolinfo, userApproveRes);
                    userApproveRes.setStartdate(xslSchoolinfo.getStartdate().substring(0, 11));
                    Byte degree = xslSchoolinfo.getDegree();

                    if(degree == 2){
                        userApproveRes.setDegree("本科");
                    }

                    if(degree == 5){
                        userApproveRes.setDegree("专科");
                    }

                    if(degree == 6){
                        userApproveRes.setDegree("研究生");
                    }

                    if(degree == 7){
                        userApproveRes.setDegree("博士");
                    }

                }
            }

			userApproveResList.add(userApproveRes);
		}

		pageObject.setData(userApproveResList);

		return pageObject;
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
        //防止空指针异常
        if( xslUser == null ){
            return false;
        }

        try {
            xslUser.setUpdatedate(new Date());
            int n = xslUserMapper.updateByUserIdSelective(xslUser);
            if(n > 0){
                return true;
            }

        }catch (Exception e){
            logger.error("用户审核操作:{}" + e);
            throw new RuntimeException(e);
        }
        return false;
    }

    @SystemServiceLog(description = "用户总数查询Service")
    @Override
    public Integer countUser()  {
        XslUserExample example = new XslUserExample();
        Integer count = 0;
        try{
            count  = xslUserMapper.countByExample(example);
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
            xslHunter.setLasttime(new Date());//由于没有0000-00-00所以设置一下时间
            this.xslHunterMapper.insertSelective(xslHunter);
            //生成masterID
            XslMaster xslMaster = new XslMaster();
            xslMaster.setDescr(" ");//防止注入失败
            xslMaster.setLastaccdate(new Date());
            this.xslMasterMapper.insertSelective(xslMaster);
            //设置hunterID
            xslUser.setHunterid(xslHunter.getHunterid());
            //设置masterID
            xslUser.setMasterid(xslMaster.getMasterid());
            //设置修改时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date1 = sdf.format(new Date());
            xslUser.setUpdatedate(new Date());
        }catch (Exception e){
            logger.error("雇主、猎人账户生成异常  :" + e.getMessage());
            return null;
        }
        return xslUser;
    }

    private XslSchoolinfo mockXslSchoolinfo(){
        /**
         *
         * 功能描述: 模拟生成学校信息
         *
         * @param: []
         * @return: xsl.erp.pojo.XslSchoolinfo
         * @auther: 11432_000
         * @date: 2018/10/4 13:37
         */
        XslSchoolinfo schoolinfo = new XslSchoolinfo();
        schoolinfo.setSchoolid(UUID.randomUUID().toString());
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
        /**
         *
         * 功能描述: 模拟生成雇主信息
         *
         * @param: [phone]
         * @return: xsl.erp.pojo.XslMaster
         * @auther: 11432_000
         * @date: 2018/10/4 13:38
         */
        XslMaster xslMaster = new XslMaster();
        xslMaster.setMasterid(UUID.randomUUID().toString());
        xslMaster.setLevel((short)1);
        xslMaster.setEmpirical(0);
        xslMaster.setTaskaccnum(0);
        xslMaster.setTaskrevokenum(0);
        xslMaster.setCredit((short)100);
        xslMaster.setDescr("雇主" + phone);
        xslMaster.setState(true);
        xslMaster.setLastaccdate(new Date());
        return xslMaster;
    }

    private XslHunter mockXslHunter(String phone){
        /**
         *
         * 功能描述:模拟生成猎人信息
         *
         * @param: [phone]
         * @return: xsl.erp.pojo.XslHunter
         * @auther: 11432_000
         * @date: 2018/10/4 13:38
         */
        XslHunter xslHunter = new XslHunter();
        xslHunter.setHunterid(UUID.randomUUID().toString());
        xslHunter.setLevel((short)1);
        xslHunter.setEmpirical(0);
        xslHunter.setTaskaccnum(0);
        xslHunter.setTaskfailnum(0);
        xslHunter.setCredit((short)100);
        xslHunter.setDescr("猎人" + phone);
        xslHunter.setState(true);
        xslHunter.setLasttime(new Date());
        return xslHunter;
    }

    private String setHunterAndGetHunterId(String phone){
        XslHunter xslHunter = mockXslHunter(phone);
        int i = xslHunterMapper.insertSelective(xslHunter);
        if(i > 0){
            return xslHunter.getHunterid();
        }
       return "";
    }

    private String setMasterAndGetMasterId(String phone){
        XslMaster xslMaster = mockXslMaster(phone);
        int i = xslMasterMapper.insertSelective(xslMaster);
        if(i > 0){
            return xslMaster.getMasterid();
        }
        return "";
    }

    private String setSchoolInfoAndGetSchoolInfo(){
        XslSchoolinfo schoolinfo = mockXslSchoolinfo();
        int i = xslSchoolinfoMapper.insertSelective(schoolinfo);
        if(i > 0){
            return schoolinfo.getSchoolid();
        }
        return "";
    }



}
