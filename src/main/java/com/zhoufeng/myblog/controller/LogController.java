package com.zhoufeng.myblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhoufeng.myblog.entity.Log;
import com.zhoufeng.myblog.service.LogService;
import com.zhoufeng.myblog.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Resource
    private LogService logService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result list(@RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer pageSize) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        List<Log> logs = logService.list();
        PageInfo<Log> pageInfo = new PageInfo<>(logs == null ? new java.util.ArrayList<>() : logs);
        pageInfo.setList(logs);
        return new Result(1, "success", pageInfo);
    }
}

