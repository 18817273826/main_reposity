package com.stylefeng.guns.rest.modular.film.vo;

import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.stylefeng.guns.api.film.vo.FilmVO;
import lombok.Data;

import java.util.List;

//就是ResponseVO中的data参数
@Data
public class FileIndexVO {

   private List<BannerVO> bannets;
   private FilmVO hotFilms;
   private FilmVO soonFilms;
   private List<FilmInfo> boxRanking;
   private List<FilmInfo> expectRanking;
   private List<FilmInfo> top100;
}
