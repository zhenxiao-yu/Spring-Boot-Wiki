package com.zhenxiao.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhenxiao.wiki.entity.Ebook;
import com.zhenxiao.wiki.entity.EbookExample;
import com.zhenxiao.wiki.mapper.EbookMapper;
import com.zhenxiao.wiki.request.EbookReq;
import com.zhenxiao.wiki.response.EbookRes;
import com.zhenxiao.wiki.response.PageRes;
import com.zhenxiao.wiki.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);
    
    @Resource
    private EbookMapper ebookMapper;

    public PageRes<EbookRes> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        //dynamic sql syntax
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        //using page helper plugin
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        // get page info of ebooklist
        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        LOG.info("Total Row Count: {}", pageInfo.getTotal());
        LOG.info("Total Page Count: {}", pageInfo.getPages());

//        //declare new arraylist variable to hold list of ebook responses
//        List<EbookRes> responseList = new ArrayList<>();
//        //iterate through each instance in 'ebooklist' and covert them to 'EbookRes' instances
//        for (Ebook ebook : ebookList) {
//            // EbookRes ebookRes = new EbookRes(); //create a new 'ebookRes' instance
//            //copy properties of each ebook in ebookList to new instances of ebookRes
//            EbookRes ebookRes = CopyUtil.copy(ebook, EbookRes.class);
//            responseList.add(ebookRes);
//        }

        List<EbookRes> list = CopyUtil.copyList(ebookList, EbookRes.class);

        PageRes<EbookRes> pageRes = new PageRes();
        pageRes.setTotal(pageInfo.getTotal());
        pageRes.setList(list);
        return pageRes;
    }
}
