package com.zhoufeng.myblog.service;

import com.zhoufeng.myblog.entity.Tag;

import java.util.List;

public interface TagService {

    Integer count();

    Tag getById(Integer id);

    List<Tag> getLikeName(String name);

    List<Tag> getList();

    List<Tag> getListByArticleId(Integer articleId);

    Integer add(Tag tag);

    Integer removeById(Integer id);

    Integer modify(Tag tag);
}
