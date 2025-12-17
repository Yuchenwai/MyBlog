package com.zhoufeng.myblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.zhoufeng.myblog.entity.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {
    Integer count();

    List<Comment> listAll();

    Comment selectById(Integer id);

    Integer insert(Comment comment);

    Integer deleteById(Integer id);
}
