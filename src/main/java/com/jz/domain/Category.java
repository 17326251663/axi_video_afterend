package com.jz.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author gukaipeng
 * @email gukaipeng@czrj.fun
 * @date 2020/5/12
 */
@Data
@Table(name = "axxi_type")
public class Category {
    @Id
    private Integer tid;
    @Column(name = "typeName")
    private String typeName;
    @Column(name = "tUrl")
    private String tUrl;
    @Column(name = "realUrl")
    private String realUrl;
    private String type;
}
