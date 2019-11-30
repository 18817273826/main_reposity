package com.stylefeng.guns.rest.modular.film.vo;

import com.stylefeng.guns.api.film.vo.CatVO;
import com.stylefeng.guns.api.film.vo.SourceVO;
import com.stylefeng.guns.api.film.vo.YearVO;
import lombok.Data;

import javax.annotation.security.DenyAll;
import java.io.Serializable;
import java.util.List;

@Data
public class FilmConditionVO {
    private List<CatVO> catVOS;
    private List<SourceVO> sourceVOS;
    private List<YearVO> yearVOS;
}
