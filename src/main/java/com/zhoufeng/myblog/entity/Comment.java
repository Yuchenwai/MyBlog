package com.zhoufeng.myblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Integer id;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createTime;
    private User user;
}
