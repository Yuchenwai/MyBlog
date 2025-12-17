package com.zhoufeng.myblog.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {
    private Integer id;
    private String ip;
    private String url;
    private String method;
    private String args;
    private LocalDateTime operateTime;
}
