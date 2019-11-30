package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.CatVO;
import com.stylefeng.guns.api.film.vo.SourceVO;
import com.stylefeng.guns.api.film.vo.YearVO;
import com.stylefeng.guns.rest.modular.film.vo.FileIndexVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.List;

//展示首页信息
/*
*
* 这里gateway的作用：
*    API网关：
*        1.功能聚合{API聚合}，一次拿全，不用多次http请求
*
* */
@RestController
@RequestMapping(value = "/film/")
public class FilmController {

    private static final String IMG = "www.baidu.com";

    @Reference(interfaceClass = FilmServiceAPI.class)
    private FilmServiceAPI filmServiceAPI;

    //首页接口
    @RequestMapping(value = "getIndex", method = RequestMethod.GET)
    public com.stylefeng.guns.rest.modular.film.vo.ResponseVO<FileIndexVO> getIndex(){
        FileIndexVO fileIndexVO = new FileIndexVO();
        //banne栏
        fileIndexVO.setBannets(filmServiceAPI.getBannerVO());
        //热映电影
        fileIndexVO.setHotFilms(filmServiceAPI.getHotFilms(true, 8));
        //即将上映电影
        fileIndexVO.setSoonFilms(filmServiceAPI.getSoonFilms(true, 8));
        //票房排行
        fileIndexVO.setBoxRanking(filmServiceAPI.getBoxRanking());
        //最受欢迎榜单
        fileIndexVO.setExpectRanking(filmServiceAPI.getExpectRanking());
        //获取前一百
        fileIndexVO.setTop100(filmServiceAPI.getTop());
        //这里返回的很巧妙
      return com.stylefeng.guns.rest.modular.film.vo.ResponseVO.success(IMG, fileIndexVO);
    }

    //影片条件查询接口
    @RequestMapping(value = "getConditionList", method = RequestMethod.GET)
    public ResponseVO<FilmConditionVO> getConditionList(@RequestParam(name = "catId", required = false, defaultValue = "99") String catId,
                                                        @RequestParam(name = "sourceId", required = false, defaultValue = "99") String sourceId,
                                                        @RequestParam(name = "yearId", required = false, defaultValue = "99")String yearId){
       FilmConditionVO conditionVO = new FilmConditionVO();
       //类型集合
           //判断集合是否存在catId，如果存在，将对应的实体变为isActive
                                  //如果不存在，则将全部实体变为isActive
        List<CatVO> cats = filmServiceAPI.getCats();
        for (CatVO cat : cats){
           if (cat.getCatId().equals(catId)){
               cat.setActive(true);
           }else {
               cat.setActive(false);
           }
        }
        //片源集合
        List<SourceVO> sources = filmServiceAPI.getSources();
        for (SourceVO source : sources){
            if (source.getSourceId().equals(sourceId)){
                source.setActive(true);
            }else {
                source.setActive(false);
            }
        }
        //年代集合
        List<YearVO> years = filmServiceAPI.getYears();
        for (YearVO year : years){
            if (year.getYearId().equals(yearId)){
                year.setActive(true);
            }else {
                year.setActive(false);
            }
        }
        conditionVO.setCatVOS(cats);
        conditionVO.setSourceVOS(sources);
        conditionVO.setYearVOS(years);
        return ResponseVO.success(conditionVO);
    }

}
