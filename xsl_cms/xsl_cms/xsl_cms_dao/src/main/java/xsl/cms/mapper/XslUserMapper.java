package xsl.cms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xsl.cms.pojo.XslUser;
import xsl.cms.pojo.XslUserExample;

public interface XslUserMapper {
    int countByExample(XslUserExample example);

    int deleteByExample(XslUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XslUser record);

    int insertSelective(XslUser record);

    List<XslUser> selectByExample(XslUserExample example);

    XslUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XslUser record, @Param("example") XslUserExample example);

    int updateByExample(@Param("record") XslUser record, @Param("example") XslUserExample example);

    int updateByPrimaryKeySelective(XslUser record);

    int updateByPrimaryKey(XslUser record);
}