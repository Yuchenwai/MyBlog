package com.zhoufeng.myblog.service;

import com.zhoufeng.myblog.entity.Comment;

import java.util.List;

public interface CommentService {
    Integer count();

    Comment getById(Integer id);

    List<Comment> getList();

    Integer add(Comment comment);

    Integer remove(Integer id);
}
