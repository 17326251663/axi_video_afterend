package com.jz.domain;

import lombok.Data;

/**
 * @author gukaipeng
 * @email gukaipeng@czrj.fun
 * @date 2020/5/12
 */
@Data
public class GeoLocation {
    private String countryCode;
    private String countryName;
    private String region;
    private String regionName;
    private String city;
    private String postalCode;
    private String latitude;
    private String longitude;
}
