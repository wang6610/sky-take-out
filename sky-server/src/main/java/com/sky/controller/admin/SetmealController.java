package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/admin/setmeal")
@RestController
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    // 新增套餐
    @PostMapping
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result add(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐{}",setmealDTO);
        setmealService.add(setmealDTO);
        return Result.success();
    }

    // 分页查询
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult pageResult = setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    // 批量删除
    @DeleteMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result deleteBatch(@RequestParam List<Long> ids){
        log.info("要删除的套餐id为{}",ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    // 根据id查询
    @GetMapping("/{id}")
    public Result<SetmealVO> getSetmealById(@PathVariable Long id){
        log.info("需要查询的套餐id为{}",id);
        SetmealVO setmealVO = setmealService.getSetmealById(id);
        return Result.success(setmealVO);
    }

    // 修改套餐
    @PutMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("需要修改的套餐为{}",setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }

    // 套餐起售、停售
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result setStatus(@PathVariable Integer status,Long id){
        log.info("设置套餐状态：{}",status == 1 ? "起售" : "停售");
        setmealService.setStatus(status,id);
        return Result.success();
    }
}
