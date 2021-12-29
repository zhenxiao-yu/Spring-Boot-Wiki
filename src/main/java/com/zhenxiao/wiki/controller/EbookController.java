package com.zhenxiao.wiki.controller;

import com.zhenxiao.wiki.request.EbookQueryReq;
import com.zhenxiao.wiki.request.EbookSaveReq;
import com.zhenxiao.wiki.response.CommonRes;
import com.zhenxiao.wiki.response.EbookQueryRes;
import com.zhenxiao.wiki.response.PageRes;
import com.zhenxiao.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    // get the entire list of ebook
    @GetMapping("/list")
    public CommonRes list(EbookQueryReq req) {
        CommonRes<PageRes<EbookQueryRes>> resp = new CommonRes<>();
        PageRes<EbookQueryRes> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    // save ebook info
    @PostMapping("/save")
    //  use 'RequestBody to submit as JSON
    public CommonRes save(@RequestBody EbookSaveReq req) {
        CommonRes resp = new CommonRes<>();
        ebookService.save(req);
        return resp;
    }
}
