package com.hcmute.foodapphomework.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hcmute.foodapphomework.Dao.FoodDao;
import com.hcmute.foodapphomework.Entity.Food;

@Database(entities = {Food.class}, version = 1)
public abstract class FoodDatabase extends RoomDatabase {
    public abstract FoodDao foodDao();
    private static FoodDatabase instance;

    public static synchronized FoodDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FoodDatabase.class, "my_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
