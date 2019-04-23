package xsl.erp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.erp.mapper.XslLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xsl.erp.annotation.SystemServiceLog;
import xsl.erp.pojo.XslLog;
import xsl.erp.pojo.XslLogExample;
import xsl.erp.pojo.common.PageObject;
import xsl.erp.service.XslControllerLogService;

import javax.annotation.Resource;
import java.util.List;

/**
 *  XslControllerLogService 页面的服务层
 *  @author 王坤
 */
@Service
public class XslControllerLogServiceImpl implements XslControllerLogService {
    /* 控制日志日志 */
    Logger logger = LoggerFactory.getLogger(XslControllerLogServiceImpl.class);

    @Resource
    protected XslLogMapper xslLogMapper;

    /**
     * 页面数据的查询
     * @param page 分页查询页数
     * @param rows 分页查询行数
     * @return 返回一个数据的列表包含data和total
     */
    @SystemServiceLog(description = "Controller日志分页查询Service")
    @Override
    public PageObject SelectControllerLogAll(Integer page, Integer rows ) {
        String  tag = "Controller日志分页查询";
        PageObject object = new PageObject();
        try{
            XslLogExample example = new XslLogExample();
            PageHelper.startPage(page,rows);//进行分页
            List<XslLog> list = this.xslLogMapper.selectByExample(example);
            object.setData(list);
            PageInfo<XslLog> info = new PageInfo<XslLog>(list);//得到分页的信息
            //得到分页的总数量
            object.setTotal(info.getTotal());
        }catch (Exception e){
            logger.error(tag + "异常警报 :" + e.getMessage());
        }finally {
            return object;
        }

    }
}
