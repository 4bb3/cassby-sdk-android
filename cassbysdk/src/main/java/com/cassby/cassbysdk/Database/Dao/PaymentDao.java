package com.cassby.cassbysdk.Database.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cassby.cassbysdk.Entities.CheckItem;
import com.cassby.cassbysdk.Entities.Payment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface PaymentDao {

    @Query("SELECT * FROM `payment`")
    Maybe<List<Payment>> getAll();

    @Insert
    void insertPayment(Payment payment);

    @Query("DELETE FROM `payment` WHERE uuid = :uuid")
    void deleteByUuid(String uuid);

}
