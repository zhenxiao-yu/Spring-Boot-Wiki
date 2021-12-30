package com.zhenxiao.wiki.controller;

import com.zhenxiao.wiki.request.CategoryQueryReq;
import com.zhenxiao.wiki.request.CategorySaveReq;
import com.zhenxiao.wiki.response.CategoryQueryRes;
import com.zhenxiao.wiki.response.CommonRes;
import com.zhenxiao.wiki.response.PageRes;
import com.zhenxiao.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    // get the entire list of category
    @GetMapping("/all")
    public CommonRes all() {
        CommonRes<List<CategoryQueryRes>> res = new CommonRes<>();
        List<CategoryQueryRes> list = categoryService.all();
        res.setContent(list);
        return res;
    }

    // get the query result of category
    @GetMapping("/list")
    public CommonRes list(@Valid CategoryQueryReq req) {
        CommonRes<PageRes<CategoryQueryRes>> res = new CommonRes<>();
        PageRes<CategoryQueryRes> list = categoryService.list(req);
        res.setContent(list);
        return res;
    }

    // save category info
    @PostMapping("/save")
    //  use 'RequestBody to submit as JSON
    public CommonRes save(@Valid @RequestBody CategorySaveReq req) {
        CommonRes res = new CommonRes<>();
        categoryService.save(req);
        return res;
    }

    // delete category
    @DeleteMapping("/delete/{id}")
    public CommonRes delete(@PathVariable Long id) {
        CommonRes res = new CommonRes<>();
        categoryService.delete(id);
        return res;
    }
}
