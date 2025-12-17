package com.zhoufeng.myblog.service.Impl;

import com.zhoufeng.myblog.entity.Log;
import com.zhoufeng.myblog.mapper.LogMapper;
import com.zhoufeng.myblog.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;

    @Override
    public List<Log> list() {
        return logMapper.listAll();
    }
}

