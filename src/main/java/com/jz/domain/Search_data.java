package com.jz.domain;

import lombok.Data;

import java.util.List;

/**
 * @author gukaipeng
 * @email gukaipeng@czrj.fun
 * @date 2020/5/9
 */
@Data
public class Search_data {
    private Integer searchNum = 0;
    private List<Model> modelList1;
    private List<Model> modelList2;
    private List<Model> modelList3;
}
