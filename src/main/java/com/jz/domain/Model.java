package com.jz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author gukaipeng
 * @email gukaipeng@czrj.fun
 * @date 2020/5/9
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Model {
    /** key*/
    private String key;
    /** 资源路径*/
    private String dataUrl;
    /** 图片路径*/
    private String imgUrl;
    /** 更新信息*/
    private String upInfo;
    /** 标题*/
    private String title;
    /** 标签*/
    private String remark;
    /** 来源*/
    private String source;
    /** 播放类型(年份)*/
    private String genre;
    /** 主演*/
    private String cast;
    /** 导演*/
    private String director;

    private List<Model> set;
}
