package com.jz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gukaipeng
 * @email gukaipeng@czrj.fun
 * @date 2020/5/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelAllSet {
    List<String> vidSet;
    private String cid;
}
