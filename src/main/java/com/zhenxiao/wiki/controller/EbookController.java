package com.zhenxiao.wiki.controller;

import com.zhenxiao.wiki.request.EbookQueryReq;
import com.zhenxiao.wiki.request.EbookSaveReq;
import com.zhenxiao.wiki.response.CommonRes;
import com.zhenxiao.wiki.response.EbookQueryRes;
import com.zhenxiao.wiki.response.PageRes;
import com.zhenxiao.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    // get the entire list of ebook
    @GetMapping("/list")
    public CommonRes list(@Valid EbookQueryReq req) {
        CommonRes<PageRes<EbookQueryRes>> res = new CommonRes<>();
        PageRes<EbookQueryRes> list = ebookService.list(req);
        res.setContent(list);
        return res;
    }

    // save ebook info
    @PostMapping("/save")
    //  use 'RequestBody to submit as JSON
    public CommonRes save(@Valid @RequestBody EbookSaveReq req) {
        CommonRes res = new CommonRes<>();
        ebookService.save(req);
        return res;
    }

    // delete ebook
    @DeleteMapping("/delete/{id}")
    public CommonRes delete(@PathVariable Long id) {
        CommonRes res = new CommonRes<>();
        ebookService.delete(id);
        return res;
    }
}
