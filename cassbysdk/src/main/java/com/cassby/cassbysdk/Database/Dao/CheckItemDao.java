package com.cassby.cassbysdk.Database.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cassby.cassbysdk.Entities.CheckItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface CheckItemDao {

    @Query("SELECT * FROM `checkitem` WHERE uuid_check = :uuid")
    Maybe<List<CheckItem>> getByUuid(String uuid);

    @Insert
    void insertCheckItem(CheckItem item);

    @Query("DELETE FROM `checkitem` WHERE uuid_check = :uuid")
    void deleteByUuid(String uuid);

}
