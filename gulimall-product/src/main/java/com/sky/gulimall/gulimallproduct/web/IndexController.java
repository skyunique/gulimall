package com.sky.gulimall.gulimallproduct.web;

import com.sky.gulimall.gulimallproduct.entity.CategoryEntity;
import com.sky.gulimall.gulimallproduct.service.CategoryService;
import com.sky.gulimall.gulimallproduct.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IndexController
 * @Desc
 * @Author sunqi
 * @Date 2021/11/4
 **/
@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/", "index.html"})
    public String getIndex(Model model) {
        //获取所有的一级分类
        List<CategoryEntity> categories = categoryService.getLevel1Catagories();

        model.addAttribute("catagories", categories);

        return "index";
    }

    @ResponseBody
    @GetMapping("/index/json/catalog.json")
    public Map<String,List<Catelog2Vo>> getCatalogJson(){

      Map<String, List<Catelog2Vo>> catas =  categoryService.getCatalogJson();

      return catas;
    }



}
