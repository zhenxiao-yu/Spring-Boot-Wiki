package com.zhenxiao.wiki.controller;

import com.zhenxiao.wiki.entity.Ebook;
import com.zhenxiao.wiki.response.CommonRes;
import com.zhenxiao.wiki.response.EbookRes;
import com.zhenxiao.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

//
    @GetMapping("/list")
    public CommonRes list(Ebook req) {
        CommonRes<List<EbookRes>> resp = new CommonRes<>();
        List<EbookRes> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
