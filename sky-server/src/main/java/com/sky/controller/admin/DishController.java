package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    // 新增菜品
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);

        String key = "dish_" + dishDTO.getCategoryId();
        CleanCache(key);
        return Result.success();
    }

    // 分页查询
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("分页查询");
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    // 批量删除
    @DeleteMapping
    public Result deleteBatch(@RequestParam List<Long> ids){
        log.info("批量删除菜品的id为{}",ids);
        dishService.deleteBatch(ids);

        CleanCache("dish_*");
        return Result.success();
    }

    // 根据id查询菜品
    @GetMapping("/{id}")
    public Result<DishVO> getDishById(@PathVariable Long id){
        log.info("要查询的菜品id为{}",id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    // 修改菜品
    @PutMapping
    public Result updateDish(@RequestBody DishDTO dishDTO){
        log.info("修改菜品，{}",dishDTO);
        dishService.updateDish(dishDTO);

        CleanCache("dish_*");
        return Result.success();
    }

    // 菜品起售、停售
    @PostMapping("status/{status}")
    public Result setStatus(@PathVariable Integer status,Long id){
        log.info("根据id{}修改菜品的状态：{}",id ,status);
        dishService.setStatus(status,id);

        CleanCache("dish_*");
        return Result.success();
    }

    // 根据分类id查询菜品
    @GetMapping("list")
    public Result<List<Dish>> getDishList(Long categoryId){
        log.info("需要查询的分类菜品id为{}",categoryId);
        List<Dish> list = dishService.getDishList(categoryId);
        return Result.success(list);
    }

    // 清理redis缓存
    private void CleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
