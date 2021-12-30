package com.zhenxiao.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhenxiao.wiki.entity.Category;
import com.zhenxiao.wiki.entity.CategoryExample;
import com.zhenxiao.wiki.mapper.CategoryMapper;
import com.zhenxiao.wiki.request.CategoryQueryReq;
import com.zhenxiao.wiki.request.CategorySaveReq;
import com.zhenxiao.wiki.response.CategoryQueryRes;
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
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);
    
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    // return complete list of categories
    public PageRes<CategoryQueryRes> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        //using page helper plugin
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        // get page info of category list
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("Total Row Count: {}", pageInfo.getTotal());
        LOG.info("Total Page Count: {}", pageInfo.getPages());

        List<CategoryQueryRes> list = CopyUtil.copyList(categoryList, CategoryQueryRes.class);
        PageRes<CategoryQueryRes> pageRes = new PageRes();
        pageRes.setTotal(pageInfo.getTotal());
        pageRes.setList(list);
        return pageRes;
    }

    // save category info
    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //if object has no id, execute add
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            //if object has id, execute update
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    // delete category
    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
