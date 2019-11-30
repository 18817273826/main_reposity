package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Service(interfaceClass = FilmServiceAPI.class)
public class DefaultFilmServiceImpl implements FilmServiceAPI {

    @Autowired
    private MoocBannerTMapper moocBannerTMapper;

    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;

    @Autowired
    private MoocFilmTMapper moocFilmTMapper;

    @Autowired
    private MoocCatDictTMapper moocCatDictTMapper;

    @Autowired
    private MoocSourceDictTMapper moocSourceDictTMapper;

    @Autowired
    private MoocYearDictTMapper moocYearDictTMapper;
    //正在热映 1  即将热映 2   film_status
    //票房，评分，预售
    @Override
    public List<BannerVO> getBannerVO() {
        List<MoocBannerT> moocBannerTList = moocBannerTMapper.selectList(null);
        List<BannerVO> bannerVOList1 = new ArrayList<>();
        for (MoocBannerT moocBannerT : moocBannerTList) {
            BannerVO bannerVO = new BannerVO();

            bannerVO.setBannerId(moocBannerT.getUuid() + "");
            bannerVO.setBannerUrl(moocBannerT.getBannerUrl());
            bannerVO.setBannerAddress(moocBannerT.getBannerAddress());

            bannerVOList1.add(bannerVO);
        }
        return bannerVOList1;
    }
    //isLimit是针对是否首页
    @Override
    public FilmVO getHotFilms(boolean isLimit, int num) {/*
       public class FilmVO implements Serializable {
           private String filmNum;
           List<FilmInfo> filmInfos;
       }
    }*/
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> fileInfoList;

        //设置热映影片的限制条件   "film_status", "1"   正在热映
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        //判断是否是首页需要内容
        if(isLimit){
            //Page是RowBounds的子类
            //是，限制，返回8个
           Page<MoocFilmT> page = new Page<>(1, num);
           List<MoocFilmT> moocFilmTList = moocFilmTMapper.selectPage(page, entityWrapper);
           fileInfoList = moocFilm2FilmInfo(moocFilmTList);
           filmVO.setFilmNum(moocFilmTList.size()+"");
           filmVO.setFilmInfos(fileInfoList);
        }else {
            //不是，分页
        }
        return null;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int num) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> fileInfoList;

        //设置热映影片的限制条件   "film_status", "2"   即将热映
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");

        //判断是否是首页需要内容
        if(isLimit){
            //是，限制，返回8个
            Page<MoocFilmT> page = new Page<>(1, num);
            List<MoocFilmT> moocFilmTList = moocFilmTMapper.selectPage(page, entityWrapper);
            fileInfoList = moocFilm2FilmInfo(moocFilmTList);
            filmVO.setFilmNum(moocFilmTList.size()+"");
            filmVO.setFilmInfos(fileInfoList);
        }else {
            //不是，分页
        }
        return null;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        List<FilmInfo> filmInfos;
        //正在上映  票房前十
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<MoocFilmT> page =  new Page<>(1,10,"film_box_office");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);

        filmInfos = moocFilm2FilmInfo(moocFilms);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        //即将上映，预售票房前十
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");

        Page<MoocFilmT> page =  new Page<>(1,10,"film_preSaleNum");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
        List<FilmInfo> filmInfos = moocFilm2FilmInfo(moocFilms);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getTop() {
        //正在上映，评分前十
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<MoocFilmT> page =  new Page<>(1,10,"film_score");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
        List<FilmInfo> filmInfos = moocFilm2FilmInfo(moocFilms);
        return filmInfos;
    }

    @Override
    public List<CatVO> getCats() {
        List<CatVO> catVOList = new ArrayList<>();
        //查询实体类对象 ---MoocCatDictT
        List<MoocCatDictT> moocCatDictTS = moocCatDictTMapper.selectList(null);
        //将实体类对象转化成业务对象
        for (MoocCatDictT moocCatDictT : moocCatDictTS){
            CatVO catVO = new CatVO();
            catVO.setCatId(moocCatDictT.getUuid()+"");
            catVO.setCatName(moocCatDictT.getShowName());
            //catVO.setActive(true); 用来判断哪个字段被选中
            catVOList.add(catVO);
        }
        System.out.println("catVOList:"+catVOList);
        return catVOList;
    }

    @Override
    public List<SourceVO> getSources() {
        List<SourceVO> sourceVOList = new ArrayList<>();
       //获取实体类对象
       List<MoocSourceDictT> moocSourceDictTS = moocSourceDictTMapper.selectList(null);
       //将实体类转换成业务对象
        for (MoocSourceDictT moocSourceDictT : moocSourceDictTS){
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(moocSourceDictT.getUuid()+"");
            sourceVO.setSourceName(moocSourceDictT.getShowName());
            sourceVOList.add(sourceVO);
        }
        return sourceVOList;
    }

    @Override
    public List<YearVO> getYears() {
        List<YearVO> yearVOList = new ArrayList<YearVO>();
        //获取实体类对象
        List<MoocYearDictT> moocYearDictTS = moocYearDictTMapper.selectList(null);
        //将实体类转换成业务对象
        for (MoocYearDictT moocYearDictT : moocYearDictTS){
            YearVO yearVO = new YearVO();
            yearVO.setYearId(moocYearDictT.getUuid()+"");
            yearVO.setYearName(moocYearDictT.getShowName());
            yearVOList.add(yearVO);
        }
        return yearVOList;
    }


    public List<FilmInfo> moocFilm2FilmInfo(List<MoocFilmT> moocFilmTS){
        //这里new过了
        List<FilmInfo> filmInfos = new ArrayList<>();
        for (MoocFilmT moocFilmT : moocFilmTS){
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setFilmId(moocFilmT.getUuid()+"");
            filmInfo.setImgAddress(moocFilmT.getImgAddress());
            filmInfo.setFilmType(moocFilmT.getFilmType()+"");
            filmInfo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));
            filmInfo.setFilmScore(moocFilmT.getFilmSource()+"");
            filmInfo.setScore(moocFilmT.getFilmScore());
            filmInfo.setFilmName(moocFilmT.getFilmName());
            filmInfo.setBoxNum(moocFilmT.getFilmBoxOffice());
            filmInfos.add(filmInfo);
        }
        return filmInfos;
    }
}
