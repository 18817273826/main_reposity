package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BannerVO  implements Serializable {
    public String bannerId;
    public String bannerAddress;
    public String bannerUrl;

}
