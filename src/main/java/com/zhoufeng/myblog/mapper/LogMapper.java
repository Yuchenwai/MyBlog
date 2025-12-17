package com.zhoufeng.myblog.mapper;

import com.zhoufeng.myblog.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LogMapper {
    @Insert("insert into log (ip, url, args, method, operate_time) values (#{ip},#{url},#{args},#{method},#{operateTime})")
    void insert(Log log);

    @Select("select * from log order by id desc")
    java.util.List<Log> listAll();
}
