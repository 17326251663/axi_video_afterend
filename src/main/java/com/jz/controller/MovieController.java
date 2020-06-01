package com.jz.controller;

import com.jz.common.BaseResult;
import com.jz.domain.*;
import com.jz.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author gukaipeng
 * @email gukaipeng@czrj.fun
 * @date 2020/5/6
 */
@RestController
@RequestMapping("/tv")
public class MovieController {


    @Resource
    MovieService movieService;

    @GetMapping("/imax")
    public BaseResult<Model> findIMAX() throws Exception {
        return BaseResult.ok("获取成功",movieService.findImax());
    }

    @GetMapping("/album")
    public BaseResult<Album> findAllAlbum() throws IOException {
        return BaseResult.ok("获取成功",movieService.findAllAlbum());
    }

    @GetMapping("/search")
    public BaseResult<Search_data> search(String keyword) throws IOException {

        if ("".equals(keyword)||keyword==null)
            return BaseResult.error("请输入查询条件");

        keyword = keyword.replaceAll(" ","");

        try {
            return BaseResult.ok("获取成功",movieService.search(keyword));
        } catch (IOException e) {
            return BaseResult.error("解析失败");
        }
    }

    @GetMapping("/getInfo")
    public BaseResult<Model> getModelInfo(String url){

        if ("".equals(url)||url==null)
            return BaseResult.error("解析失败,路径不存在");

        try {

            Model modelInfo = movieService.getModelInfo(url);
            //获取最后资源再次进行解析

            if(modelInfo.getSet().size()!=0){
                //获取最新更新
                modelInfo = movieService.getModelInfo(modelInfo.getSet().get(modelInfo.getSet().size()-1).getDataUrl());
            }

            return BaseResult.ok("获取成功",modelInfo);
        } catch (Exception e) {
            return BaseResult.error("解析失败");
        }

    }

    @GetMapping("/url_format")
    public BaseResult<String> url_format(String url) throws IOException {
       return BaseResult.ok("解析成功",movieService.urlFormat(url));
    }

    @GetMapping("/category")
    public BaseResult<Category> getCategory(){
        return BaseResult.ok("获取成功",movieService.getCategory());
    }

    @GetMapping("/all")
    public BaseResult<ModelAllSet> getAllTxTvByUrl(String url) throws IOException {
        return BaseResult.ok("获取成功",movieService.getAllTxTvByUrl(url));
    }

}
