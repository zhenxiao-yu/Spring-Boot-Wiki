package com.zhenxiao.wiki.controller;

import com.zhenxiao.wiki.request.EbookReq;
import com.zhenxiao.wiki.response.CommonRes;
import com.zhenxiao.wiki.response.EbookRes;
import com.zhenxiao.wiki.response.PageRes;
import com.zhenxiao.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

//
    @GetMapping("/list")
    public CommonRes list(EbookReq req) {
        CommonRes<PageRes<EbookRes>> resp = new CommonRes<>();
        PageRes<EbookRes> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
