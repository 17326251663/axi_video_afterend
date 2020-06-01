package com.jz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author gukaipeng
 * @email gukaipeng@czrj.fun
 * @date 2020/5/12
 */
@Table(name = "aaxi_video_visitor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vid;
    private String ip;
    private String url;
    @Column(name = "visitortime")
    @JsonFormat(pattern = "yyyy-MM-hh HH:ss:mm",timezone = "GMT+8")
    private Date visitorTime;
    private String  address;
    @Column(name = "visitorclass")
    private String visitorClass;
    private String args;


}
