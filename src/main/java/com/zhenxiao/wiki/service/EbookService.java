package com.zhenxiao.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhenxiao.wiki.entity.Ebook;
import com.zhenxiao.wiki.entity.EbookExample;
import com.zhenxiao.wiki.mapper.EbookMapper;
import com.zhenxiao.wiki.request.EbookQueryReq;
import com.zhenxiao.wiki.request.EbookSaveReq;
import com.zhenxiao.wiki.response.EbookQueryRes;
import com.zhenxiao.wiki.response.PageRes;
import com.zhenxiao.wiki.util.CopyUtil;
import com.zhenxiao.wiki.util.SnowFlake;
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

    @Resource
    private SnowFlake snowFlake;

    // return complete list of ebooks
    public PageRes<EbookQueryRes> list(EbookQueryReq req) {
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
//        List<EbookQueryRes> responseList = new ArrayList<>();
//        //iterate through each instance in 'ebooklist' and covert them to 'EbookQueryRes' instances
//        for (Ebook ebook : ebookList) {
//            // EbookQueryRes ebookRes = new EbookQueryRes(); //create a new 'ebookRes' instance
//            //copy properties of each ebook in ebookList to new instances of ebookRes
//            EbookQueryRes ebookRes = CopyUtil.copy(ebook, EbookQueryRes.class);
//            responseList.add(ebookRes);
//        }

        List<EbookQueryRes> list = CopyUtil.copyList(ebookList, EbookQueryRes.class);
        PageRes<EbookQueryRes> pageRes = new PageRes();
        pageRes.setTotal(pageInfo.getTotal());
        pageRes.setList(list);
        return pageRes;
    }

    // save ebook info
    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //if object has no id, execute add
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        } else {
            //if object has id, execute update
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }
}
