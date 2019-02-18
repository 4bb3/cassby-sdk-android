package com.cassby.cassbysdk.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cassby.cassbysdk.Entities.Check;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface CheckDao {

    @Query("SELECT * FROM `check`")
    Maybe<List<Check>> getAll();

    @Insert
    void insert(Check check);

    @Query("DELETE FROM `check` WHERE uuid = :uuid")
    void deleteByUuid(String uuid);

}
