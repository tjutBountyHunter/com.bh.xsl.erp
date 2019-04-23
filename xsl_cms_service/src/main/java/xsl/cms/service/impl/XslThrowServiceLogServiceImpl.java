package xsl.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xsl.cms.mapper.XslThrowinglogMapper;
import org.springframework.stereotype.Service;
import xsl.cms.annotation.SystemServiceLog;
import xsl.cms.pojo.XslThrowinglog;
import xsl.cms.pojo.XslThrowinglogExample;
import xsl.cms.pojo.common.PageObject;
import xsl.cms.service.XslThrowServiceLogService;

import javax.annotation.Resource;
import java.util.List;

/**
 *  XslControllerLogService 页面的服务层
 *  @author 王坤
 */
@Service
public class XslThrowServiceLogServiceImpl implements XslThrowServiceLogService {

    @Resource
    protected XslThrowinglogMapper xslThrowinglogMapper;

    /**
     * 页面数据的查询
     * @param page 分页查询页数
     * @param rows 分页查询行数
     * @return 返回一个数据的列表包含data和total
     */
    @SystemServiceLog(description = "Service异常日志分页查询Service")
    @Override
    public PageObject SelectThrowServiceLogAll(Integer page, Integer rows ) {
        XslThrowinglogExample example = new XslThrowinglogExample();
        PageHelper.startPage(page,rows);//进行分页
        List<XslThrowinglog> list = this.xslThrowinglogMapper.selectByExample(example);
        PageObject object = new PageObject();
        object.setData(list);
        PageInfo<XslThrowinglog> info = new PageInfo<XslThrowinglog>(list);//得到分页的信息
        //得到分页的总数量
        object.setTotal(info.getTotal());
        return object;
    }
}
