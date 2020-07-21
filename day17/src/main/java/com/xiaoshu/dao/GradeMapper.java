package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Grade;
import com.xiaoshu.entity.GradeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GradeMapper extends BaseMapper<Grade> {
    long countByExample(GradeExample example);

    int deleteByExample(GradeExample example);

    List<Grade> selectByExample(GradeExample example);

    int updateByExampleSelective(@Param("record") Grade record, @Param("example") GradeExample example);

    int updateByExample(@Param("record") Grade record, @Param("example") GradeExample example);
}