package com.jz.domain;

import lombok.Data;

import java.util.List;

/**
 * @author gukaipeng
 * @email gukaipeng@czrj.fun
 * @date 2020/5/9
 */
@Data
public class Album {

    private List<Model> modelList;
    private String title;
}
