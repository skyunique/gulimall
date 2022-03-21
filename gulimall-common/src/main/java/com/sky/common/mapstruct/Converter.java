package com.sky.common.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.xml.transform.Source;
import java.lang.annotation.Target;

/**
 * @ClassName Converter
 * @Desc
 * @Author sunqi
 * @Date 2021/9/16
 **/
@Mapper
public interface Converter {
    // 这个INSTANCE并不是必须的，只是为了方便调用者容易使用
    Converter INSTANCE = Mappers.getMapper(Converter.class);
    // 方法名随意，没关系，具有可读语义即可
    Object s2t(Object source);
}
