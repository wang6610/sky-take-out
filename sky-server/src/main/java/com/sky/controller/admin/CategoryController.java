package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 新增分类
    @PostMapping
    public Result save(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类");
        categoryService.save(categoryDTO);
        return Result.success();
    }

    // 分类分页查询
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分页查询");
        com.sky.result.PageResult  pageResult =categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    // 删除分类
    @DeleteMapping
    public Result deleteById(Long id){
        log.info("删除分类");
        categoryService.deleteById(id);
        return Result.success();
    }

    // 修改分类
    @PutMapping
    public Result update(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类");
        categoryService.update(categoryDTO);
        return Result.success();
    }

    // 启用禁用分类
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("启用禁用分类");
        categoryService.startOrStop(status, id);
        return Result.success();
    }


    // 根据类型查询
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
