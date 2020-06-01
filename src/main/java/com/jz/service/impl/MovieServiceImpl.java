package com.jz.service.impl;


import com.jz.common.SSLUtils;
import com.jz.domain.*;
import com.jz.mapper.CategoryMapper;
import com.jz.service.MovieService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author gukaipeng
 * @email gukaipeng@czrj.fun
 * @date 2020/5/6
 */
@Service
@Transactional
@CacheConfig(cacheNames = {"movie"})
public class MovieServiceImpl implements MovieService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    CategoryMapper categoryMapper;

    private static final String prefix = "http://m.v.qq.com";

    @Override
    @Cacheable(cacheNames = {"imax"})
    public List<Model> findImax() throws Exception {

//        //判断redis中是否有数据,没有再进行更新
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//
//        String slideshow = ops.get("tv_imax");
//
//        if (slideshow != null && !"".equals(slideshow)) {
//            return JSONArray.toList(JSONArray.fromObject(slideshow), Model.class);
//        }

        Connection connect = Jsoup.connect("https://m.v.qq.com/");

        //获取到巨幕的列表
        Elements swiper = connect.get().getElementsByClass("swiper").get(0).getElementsByClass("swiper-slide");

        //生成beanList
        List<Model> modelList = new ArrayList<>();

        for (Element list : swiper) {
            //生成bean
            Model model = new Model();
            //获取key
            String key = list.attr("key");
            //获取资源链接
            String dada_href = prefix + list.getElementsByClass("open_video").attr("href");
            //获取图片链接
            String img_src = list.getElementsByTag("img").attr("dsrc");
            //获取更新信息
            String upInfo = list.getElementsByClass("item_count").text();
            //获取title
            String title = list.getElementsByClass("item_title").text();

            //封装信息
            model.setDataUrl(prefix + dada_href);
            model.setKey(key);
            model.setDataUrl(dada_href);
            model.setImgUrl(img_src);
            model.setUpInfo(upInfo);
            model.setTitle(title);

            modelList.add(model);
        }


        //存到redis中
//        ops.set("tv_imax", new ObjectMapper().writeValueAsString(modelList), 2, TimeUnit.DAYS);

        return modelList;
    }

    @Override
    @Cacheable(cacheNames = {"album"})
    public List<Album> findAllAlbum() throws IOException {
        //获取到首页
        Connection connect = Jsoup.connect("https://m.v.qq.com/");
        //获取到container列表
        Elements container = connect.get().getElementsByClass("container").get(0).getElementsByClass("feeds_block");

        //创建专辑列表
        List<Album> albumList = new ArrayList<>();

        for (Element element : container) {

            //获取专辑title
            String a_title = element.getElementsByClass("block_title").text();

            //获取所有Model列表
            Elements a = element.getElementsByTag("a");

            //创建Model列表
            List<Model> modelList = new ArrayList<>();

            for (Element ac : a) {
                //获取model  key
                String model_key = ac.parent().attr("key");
                //获取model dataUrl
                String dataUrl = prefix + ac.attr("href");
                //获取model img
                String imgUrl = ac.getElementsByTag("img").attr("dsrc");
                //获取model upInfo
                String upInfo = ac.getElementsByClass("item_count").text();
                //获取model title
                String title = ac.getElementsByClass("item_title").text();
                //获取model remark
                String remark = ac.getElementsByClass("item_remark").text();

                //创建model并添加至列表
                modelList.add(new Model(model_key, dataUrl, imgUrl, upInfo, title, remark, "腾讯", "", "", "", null));
            }

            //创建专辑
            Album album = new Album();

            //添加model列表至album
            album.setTitle(a_title);
            album.setModelList(modelList);
            //添加album至album列表
            albumList.add(album);
        }

        return albumList;
    }

    @Override
    public Search_data search(String keyWord) throws IOException {

        //获取搜索页数据
        Connection connect = Jsoup.connect("https://m.v.qq.com/search.html?keyWord=" + keyWord);
        //获取搜索结果
        Element result = connect.get().getElementById("result");

        //获取搜索结果数量(正片)
        String num_String = result.getElementsByClass("mod_figures_title").size() == 0 ? "1" : result.getElementsByClass("mod_figures_title").get(0).text();
        //格式化
        Integer num = Integer.valueOf(num_String.replaceAll("\\D", ""));

        //声明search
        Search_data search_data = new Search_data();

        search_data.setSearchNum(num);

        //初始化Model列表1
        List<Model> modelList1 = new ArrayList<>();

        search_data.setModelList1(modelList1);

        //筛选列表1
        Elements select = result.select(".mod_figure>li");
        for (Element element : select) {
            //获取model dataURL
            String data_url1 = element.select("a").get(0).attr("href");
            //获取model imgURL
            String img_url1 = element.select("img").attr("src");
            //获取model title
            String title_1 = element.select(".figure_title").text();

            //创建model
            Model model = new Model();
            model.setTitle(title_1);
            model.setDataUrl(data_url1);
            model.setImgUrl(img_url1);

            //添加到model列表1
            modelList1.add(model);
        }

        //筛选列表2
        List<Model> modelList2 = new ArrayList<>();

        search_data.setModelList2(modelList2);

        Elements select2 = result.select(".search_item");

        for (int i = 0; i < select2.size() - 1; i++) {
            //获取每一条信息
            Element element = select2.get(i);

            //获取model dataURL
            String dataUrl = element.select("a").attr("href");
            //获取model imgURL
            String imgUrl = element.select("img").attr("src");
            //获取model title
            String title = element.select("strong").text();
            //获取model source
            String source = element.select(".figure_source").text();
            //获取model genre
            String genre = element.select(".figure_genre").text();
            //获取model cast;
            String cast = element.select(".figure_cast").text();
            //获取model director;
            String director = element.select(".figure_director").text();

            //设置model信息
            Model model = new Model("", dataUrl, imgUrl, "", title, "", source, genre, cast, director, null);

            //添加到列表
            modelList2.add(model);
        }

        return search_data;

    }

    @Override
    public Model getModelInfo(String url) throws IOException {

        Document page = Jsoup.connect(url).get();

        //获取info
        Element info = page.select(".mod_box").get(0);

        //获取title
        String title = info.select(".video_title").text();
        //获取context
        String context = info.select(".mod_video_info").text();

        Elements items = page.select(".mod_list_slider .item a");

        List<Model> set = new ArrayList<>();
        for (Element item : items) {
            Model model = new Model();
            model.setDataUrl(prefix + item.attr("href"));
            model.setTitle(item.attr("title"));
            set.add(model);
        }

        Model model = new Model();
        model.setTitle(title);
        model.setRemark(context);
        model.setSet(set);

        return model;
    }

    @Override
    public String urlFormat(String url) throws IOException {
        Element select = Jsoup.connect(url).get().select("#app section").get(0);
        System.out.println(select);
        return null;
    }

    @Override
    @Cacheable(cacheNames = {"axxi_category"})
    public List<Category> getCategory() {
        return categoryMapper.selectAll();
    }

    @Override
    public ModelAllSet getAllTxTvByUrl(String url) throws IOException {

        Elements script = Jsoup.connect(url).get().select("script");

        String LIST_INFO = "LIST_INFO";

        List<String> jishu = new ArrayList<>();

        String COVER_INFO = "";

        String text = "";

        for (Element element : script) {

            if (!element.data().contains(LIST_INFO))continue;

            text =  element.data();
        }

        if ("".equals(text)) return null;

        //获取vid队列
        Pattern LIST_INFO_Match = Pattern.compile("var LIST_INFO = \\{\"vid\":\\[(.*)]");

        //获取cid
        Pattern COVER_INFO_Match = Pattern.compile("var COVER_INFO = \\{\"id\":\"(.*)\",\"typeid");


        //匹配
        Matcher matcher1 = LIST_INFO_Match.matcher(text);
        Matcher matcher2 = COVER_INFO_Match.matcher(text);

        if (matcher1.find()){
            jishu = Arrays.asList(matcher1.group(1).replaceAll("\"","").split(","));
            System.out.println(LIST_INFO);
        }

        if(matcher2.find()){
            COVER_INFO = matcher2.group(1);
            System.out.println(COVER_INFO);
        }

        //走到这里,说明解析成功了,封装数据
        return new ModelAllSet(jishu,COVER_INFO);
    }

    public static void main(String[] args) throws Exception {
        SSLUtils.trustAllHttpsCertificates();



    }

}

















