package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;


    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
        if(queryPageRequest==null){
            queryPageRequest = new QueryPageRequest();
        }
        if(page<=0){
            page = 1;
        }
        page = page - 1 ;
        if(size<=0){
            size = 20;
        }
        //设置分页的条件
        Pageable pageable = new PageRequest(page,size);
        //分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        //封装结果
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<>();
        cmsPageQueryResult.setList(all.getContent());
        cmsPageQueryResult.setTotal(all.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS,cmsPageQueryResult);
    }
}
