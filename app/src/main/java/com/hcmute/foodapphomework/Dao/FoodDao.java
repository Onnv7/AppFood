package com.hcmute.foodapphomework.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hcmute.foodapphomework.Entity.Food;

import java.util.List;

@Dao
public interface FoodDao {
    @Insert
    void insertFood(Food food);


    @Query("SELECT * FROM Food WHERE id = :id")
    List<Food> checkExisted(String id);

    @Update
    void updateFood(Food food);

    @Query("SELECT * FROM Food")
    List<Food> getAllFoods();

    @Delete
    void deleteFood(Food food);
}
