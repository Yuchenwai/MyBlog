package com.zhoufeng.myblog.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.zhoufeng.myblog.entity.Tag;
import com.zhoufeng.myblog.service.TagService;
import com.zhoufeng.myblog.utils.Result;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Resource
    private TagService tagService;

    @GetMapping
    public Result listAll() {
        List<Tag> tagList = tagService.getList();
        if (tagList != null && tagList.size() > 0) {
            return new Result(1, "查询成功", tagList);
        }
        return new Result(0, "查询失败", null);
    }

    @GetMapping("/{tagName}")
    public Result query(@PathVariable String tagName) {
        List<Tag> tagList = tagService.getLikeName(tagName);
        if (tagList != null && tagList.size() > 0) {
            return new Result(1, "查询成功", tagList);
        }
        return new Result(0, "查询失败", null);
    }

    @GetMapping("/count")
    public Result count() {
        Integer count = tagService.count();
        if (count != null) {
            return new Result(1, "success", count);
        }
        return new Result(0, "error", null);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result add(String tagName) {
        try {
            if (tagName != null && tagName.trim().length() > 0) {
                Tag tag = new Tag();
                tag.setName(tagName);
                Integer count = tagService.add(tag);
                if (count == 1) {
                    return new Result(1, "添加成功", tag);
                }
            }
            return new Result(0, "添加失败", null);
        } catch (Exception e) {
            return new Result(0, "添加失败", null);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result modify(@PathVariable Integer id, String tagName) {
        try {
            if (tagName != null && tagName.trim().length() > 0) {
                Tag tag = new Tag();
                tag.setId(id);
                tag.setName(tagName);
                Integer count = tagService.modify(tag);
                if (count == 1) {
                    return new Result(1, "修改成功", null);
                }
            }
            return new Result(0, "修改失败", null);
        } catch (Exception e) {
            return new Result(0, "修改失败", null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result remove(@PathVariable Integer id) {
        Integer count = tagService.removeById(id);
        if (count == 1) {
            return new Result(1, "删除成功", null);
        }
        return new Result(0, "删除失败", null);
    }
}
