package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

public interface FilmServiceAPI {
    //=======首页
    //banne栏
    List<BannerVO> getBannerVO();
    //热映电影
    FilmVO getHotFilms(boolean isLimit, int num);
    //即将上映电影
    FilmVO getSoonFilms(boolean isLimit, int num);
    //票房排行
    List<FilmInfo> getBoxRanking();
    //人气排行榜单
    List<FilmInfo> getExpectRanking();
    //获取前一百
    List<FilmInfo> getTop();


    //======影片条件查询接口
    //获取影片分类
    List<CatVO> getCats();
    //获取影片来源
    List<SourceVO> getSources();
    //获取影片年代
    List<YearVO> getYears();

}
