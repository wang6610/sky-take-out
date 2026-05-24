package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    List<Category> list(Integer type);

    void save(CategoryDTO categoryDTO);


    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    void deleteById(Long id);

    void update(CategoryDTO categoryDTO);

    void startOrStop(Integer status, Long id);
}
