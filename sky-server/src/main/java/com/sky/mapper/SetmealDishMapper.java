package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    void insertBatch(List<SetmealDish> setmealDishes);

    void deleteBatch(List<Long> ids);

    List<SetmealDish> getSetmealIdsByDishId(Long id);

    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteById(Long id);
}
