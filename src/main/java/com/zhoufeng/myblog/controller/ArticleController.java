package com.zhoufeng.myblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.zhoufeng.myblog.entity.Article;
import com.zhoufeng.myblog.entity.Tag;
import com.zhoufeng.myblog.service.ArticleService;
import com.zhoufeng.myblog.service.TagService;
import com.zhoufeng.myblog.utils.Result;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private TagService tagService;

    @GetMapping
    public Result list(@RequestParam(required = false) Integer page,
                       @RequestParam(required = false) Integer pageSize,
                       @RequestParam(required = false) Integer status) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        List<Article> articleList;
        if (status == null) {
            articleList = articleService.getList();
        } else {
            articleList = articleService.getList(status);
        }
        List<Article> articles = new ArrayList<>();
        if (articleList != null && articleList.size() > 0) {
            for (Article article : articleList) {
                List<Tag> tags = tagService.getListByArticleId(article.getId());
                if (tags != null && tags.size() > 0) {
                    article.setTags(tags);
                }
                articles.add(article);
            }
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articleList == null ? new ArrayList<>() : articleList);
        pageInfo.setList(articles);
        return new Result(1, "success", pageInfo);
    }

    @GetMapping("/s")
    public Result searchArticle(Integer page, Integer pageSize,
                                @RequestParam(required = false) String title,
                                @RequestParam(required = false) Integer categoryId) {
        PageHelper.startPage(page, pageSize);
        List<Article> articleList = articleService.getListOnCondition(title, categoryId);
        if (articleList != null && articleList.size() > 0) {
            List<Article> articles = new ArrayList<>();
            for (Article article : articleList) {
                List<Tag> tags = tagService.getListByArticleId(article.getId());
                article.setTags(tags);
                articles.add(article);
            }
            PageInfo<Article> pageInfo = new PageInfo<>(articleList);
            pageInfo.setList(articles);
            return new Result(1, "success", pageInfo);
        }
        return new Result(0, "error", null);
    }

    @GetMapping("/ss")
    public Result listArticle(@RequestParam(required = false) String text) {
        List<Article> articleList = articleService.getListOnCondition(text);
        if (articleList != null && articleList.size() > 0) {
            return new Result(1, "success", articleList);
        }
        return new Result(0, "error", null);
    }

    @GetMapping("/count/{status}")
    public Result count(@PathVariable Integer status) {
        Integer count = articleService.countByStatus(status);
        if (count != null) {
            return new Result(1, "success", count);
        }
        return new Result(0, "error", null);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result modifyArticle(Article article, Integer categoryId, String tagIds) {
        Integer count = articleService.modify(article, categoryId, tagIds);
        if (count == 1) {
            return new Result(1, "修改成功", null);
        }
        return new Result(0, "修改失败", null);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result add(Article article, Integer categoryId, String tagIds) {
        int status = article.getStatus();
        Integer count = articleService.add(article, categoryId, tagIds);
        if (count == 1) {
            return new Result(1, status == 1 ? "发布成功" : "保存成功", null);
        }
        return new Result(0, status == 1 ? "发布失败" : "保存失败", null);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result remove(@PathVariable Integer id) {
        Integer count = articleService.removeById(id);
        if (count == 1) {
            return new Result(1, "删除成功", null);
        }
        return new Result(0, "删除失败", null);
    }
}
