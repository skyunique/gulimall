package com.sky.gulimall.gulimallproduct.exception;

import com.sky.common.exception.BizCodeEnum;
import com.sky.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName GulimallExceptionControllerAdvice
 * @Desc  集中处理所有异常
 * @Author sunqi
 * @Date 2021/9/10
 **/
@Slf4j
@RestControllerAdvice(basePackages = "com.sky.gulimall.gulimallproduct.controller")
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public R handlerValidException(MethodArgumentNotValidException excetion) {
        Map<String, String> map = new HashMap<>();
        BindingResult result = excetion.getBindingResult();
        result.getFieldErrors().forEach((item)->{
            String message = item.getDefaultMessage();
            String field = item.getField();
            map.put(field, message);
        });
        log.error("数据校验出现问题:{},异常类型{}",excetion.getMessage(),excetion.getClass());
        return R.error(BizCodeEnum.VAILD_EXCEPTION.getCode(), BizCodeEnum.VAILD_EXCEPTION.getMsg()).put("data", map);
    }


}
